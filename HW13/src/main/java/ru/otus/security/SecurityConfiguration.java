package ru.otus.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/h2-console/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests().antMatchers("/", "/login*").permitAll()
				.and()
				.authorizeRequests().antMatchers("/author/*/update").hasRole("ADMIN")
				.and()
				.authorizeRequests().antMatchers("/comment/**").hasRole("USER")
				.and()
				.authorizeRequests().antMatchers("/**").authenticated()
				.and()
				.authorizeRequests().anyRequest().denyAll()
				.and()
				.formLogin()
				.loginProcessingUrl("/perform_login")
				.usernameParameter("x_username")
				.passwordParameter("x_password")
				.and()
				.logout()
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/");
	}

	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
