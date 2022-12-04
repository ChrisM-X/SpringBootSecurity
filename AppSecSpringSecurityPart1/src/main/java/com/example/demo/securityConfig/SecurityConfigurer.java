package com.example.demo.securityConfig;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	/*
	 * URL Level Method Access Controls:
	 * 
	 * Basic Authentication is being used here.  In Postman under the Authorization tab, select
	 * "Basic Auth" and include the credentials for either of the 2 users created in the
	 * inMemoryUserDetailsManager() method.
	 * 
	 * The user with the role "USER" will only be authorized to create a Student Object.
	 * 
	 * The user with the role "ADMIN" will be authorized to access all of the end points on the
	 * application.
	 * 
	 * Any other requests submitted will be denied by default.
	 * 
	 * The CSRF protection is disabled here in order to allow the POST/DELETE requests to process successfully.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.httpBasic();
		
		http.authorizeRequests()
			.mvcMatchers(HttpMethod.GET, "/studentApi/getAllStudents").hasRole("ADMIN")
			.mvcMatchers(HttpMethod.GET, "/studentApi/getAllStudentsById/**").hasRole("ADMIN")
			.mvcMatchers(HttpMethod.GET, "/studentApi/getAllStudentsByName/**").hasRole("ADMIN")
			.mvcMatchers(HttpMethod.POST, "/studentApi/createStudent").hasAnyRole("USER", "ADMIN")
			.mvcMatchers(HttpMethod.PUT, "/studentApi/editStudent/**").hasAnyRole("USER", "ADMIN")
			.mvcMatchers(HttpMethod.DELETE, "/studentApi/deleteStudent/**").hasRole("ADMIN")
				.anyRequest().permitAll().and().csrf().disable();
		http.headers().frameOptions().disable();  // we need to use this in order to gain access to the h2-console when using spring security
	}  // end method configure	
	
	
	
	/*
	 * For Access Controls demo purposes, some dummy users were created using the UserDetails Class, which
	 * Spring Security uses to retrieve a user's authentication and authorization information.
	 * 
	 * These users will be used to test the URL level (mvcMatchers()) access controls functionality.
	 * 
	 * TestUser = USER role
	 * TestAdmin = ADMIN role
	 */
	@SuppressWarnings("deprecation")
	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		
		List<UserDetails> userDetailList = new ArrayList<>();
		
		userDetailList.add(User.withDefaultPasswordEncoder()
				.username("TestUser").password("TestPass").roles("USER").build());
		
		userDetailList.add(User.withDefaultPasswordEncoder()
				.username("TestAdmin").password("TestPass").roles("ADMIN").build());
		
		return new InMemoryUserDetailsManager(userDetailList);
		
	}  // end method UserDetailsService
		
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(inMemoryUserDetailsManager());
		
	} //end method

	
}  // end class
