package com.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
		
	@Bean
	public UserDetailsService users() {		
		UserDetails user = User.builder()			
			.username("guest")
			.password(passwordEncoder().encode("g1234"))
			.roles("USER")
			.build();
		UserDetails manager = User.builder()			
			.username("manager")
			.password(passwordEncoder().encode("m1234"))
			.roles("MANAGER")
			.build();	
		UserDetails admin = User.builder()			
			.username("admin")
			.password(passwordEncoder().encode("a1234"))
			.roles("ADMIN","USER","MANAGER") //exam02 에서 여러 개의 권한 출력할 때 사용할 것
			.build();
				
		return new InMemoryUserDetailsManager(user, manager, admin);	
		
	}
		
	@Bean
	public SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception {
		http
	    .csrf(AbstractHttpConfigurer::disable)    	
		.authorizeHttpRequests(
	        	authorizeRequests -> authorizeRequests        	
	        	// 권한    
	        	.requestMatchers("/login", "/signup", "/home").permitAll() // 해당 경로는 로그인 없이 접근 가능
	        	.anyRequest().authenticated() // 나머지는 로그인해야 접근 가능
	       )
		.formLogin( 
			        formLogin->formLogin				    
					   .loginPage("/login") // 사용자 정의 로그인 페이지
					   .loginProcessingUrl("/login") 
					   .defaultSuccessUrl("/home") // 로그인 성공 후 이동 페이지    
					   .usernameParameter("username") 
			 		   .passwordParameter("password")
				       .failureUrl("/login?error") // 로그인 실패 후 이동 페이지, ?error : error라는 추가 정보를 붙여서 줌 (파라미터)
			       ) 
				  
			.logout(logout -> logout
					 .logoutUrl("/logout")    // 이경로도 자동으로 처리된다. 별도로 작성할 필요가 없다.          		
			         .logoutSuccessUrl("/home")
			       );
		    return http.build();
	}
}

