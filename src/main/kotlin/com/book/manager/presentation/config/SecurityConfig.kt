package com.book.manager.presentation.config

import com.book.manager.bookmanager.BookManagerApplication
import com.book.manager.domain.enum.RoleType
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
class SecurityConfig(private val authenticationservice: AuthenticationService): WebSecurityConfigurerAdapter() {
  override fun configure(http: HttpSecurity) {
    http.authorizeRequests()
      .mvcMatchers("login").permitAll()
      .mvcMatchers("/admin/**").hasAnyAuthority(RoleType.ADMIN.toString())
      .anyRequest().authenticated()
      .and()
      .csrf().disable()
      .formLogin()
      .loginProcessingUrl("/login")
      .usernameParameter("email")
      .passwordParameter("pass")
      .successHandler(BookManagerApplicationSuccessHandler())
      .failureHandler(BookManagerApplicationFailureHandler())
      .and()
      .exceptionHandling()
      .authenticationEntryPoint(BookManagerApplicationEntryPoint())
      .accessDeniedHandler(BookManagerAccessDeniedHandler())
      .and()
      .cors()
      .configurationSource(corsConfigurationSource())
  }

  override fun configure(auth: AuthenticationManagerBuilder) {
    auth.userDetailsService(BookManagerDetailsService(authenticationservice))
      .passwordEncoder(BCryptPasswordEncoder())
  }

  private fun corsConfigurationSource(): CorsConfigurationSource {
    val corsConfiguration = CorsConfiguration()
    corsConfiguration.addAllowedMethod(CorsConfiguration.ALL)
    corsConfiguration.addAllowedHeader(CorsConfiguration.ALL)
    corsConfiguration.addAllowedOrigin("http://localhost:8081")
    corsConfiguration.allowCredentials = true

    val corsConfigurationSource = UrlBasedCorsConfigurationSource()
    corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration)

    return corsConfigurationSource
  }
}