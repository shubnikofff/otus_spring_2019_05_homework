package ru.otus.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests().antMatchers("/author/**", "/book/**", "/comment/**", "/genre/**").authenticated()
				.antMatchers("/login*").permitAll()
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
