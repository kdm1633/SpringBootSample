package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
			.requestMatchers("/webjars/**", "/css/**", "/js/**", "/h2-console/**");
	}
	
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
			.requestMatchers("/login", "/user/signup").permitAll()
			.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()	// "/h2-console/**" String doesn't work
			.requestMatchers("/admin").hasAuthority("ROLE_ADMIN")
			.anyRequest().authenticated();
		
		http.formLogin()
			.loginProcessingUrl("/login")
			.loginPage("/login")
			.failureUrl("/login?error")
			.usernameParameter("userId")
			.passwordParameter("password")
			.defaultSuccessUrl("/user/list", true);
		
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login?logout");
		
//		http.csrf().disable();
		http.csrf().ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"));
		http.headers().frameOptions().disable();	// allow h2-console embedding in iframes
		
		return http.build();
	}
	
	/*@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		PasswordEncoder passwordEncoder = passwordEncoder();
		
		UserDetails user = User.builder()
			.username("user")
			.password("user")
			.roles("GENERAL")
			.build();
		UserDetails admin = User.withUsername("admin")
			.password(passwordEncoder.encode("admin"))
			.roles("ADMIN")
			.build();
		
		return new InMemoryUserDetailsManager(user, admin);
	}*/
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http)
	        throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	            .userDetailsService(userDetailsService)
	            .passwordEncoder(passwordEncoder())
	            .and()
	            .build();
	}
}
