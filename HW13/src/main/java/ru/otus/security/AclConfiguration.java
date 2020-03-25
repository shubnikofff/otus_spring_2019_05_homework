package ru.otus.security;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.cache.ehcache.EhCacheFactoryBean;

import javax.sql.DataSource;
import java.util.Objects;

@RequiredArgsConstructor
@Configuration
public class AclConfiguration {

	private final DataSource dataSource;

	@Bean
	public AclAuthorizationStrategy aclAuthorizationStrategy() {
		return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Bean
	public PermissionGrantingStrategy permissionGrantingStrategy() {
		return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
	}

	@Bean
	public EhCacheBasedAclCache aclCache() {
		return new EhCacheBasedAclCache(
				Objects.requireNonNull(aclEhCacheFactoryBean().getObject()),
				permissionGrantingStrategy(),
				aclAuthorizationStrategy()
		);
	}

	@Bean
	public EhCacheFactoryBean aclEhCacheFactoryBean() {
		final EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
		ehCacheFactoryBean.setCacheManager(Objects.requireNonNull(aclCacheManager().getObject()));
		ehCacheFactoryBean.setCacheName("aclCache");
		return ehCacheFactoryBean;
	}

	@Bean
	public EhCacheManagerFactoryBean aclCacheManager() {
		return new EhCacheManagerFactoryBean();
	}

	@Bean
	public LookupStrategy lookupStrategy() {
		return new BasicLookupStrategy(
				dataSource,
				aclCache(),
				aclAuthorizationStrategy(),
				new ConsoleAuditLogger()
		);
	}

	@Bean
	public MethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler() {
		final DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setPermissionEvaluator(new AclPermissionEvaluator(aclService()));
		return expressionHandler;
	}

	@Bean
	public MutableAclService aclService() {
		return new JdbcMutableAclService(dataSource, lookupStrategy(), aclCache());
	}
}
