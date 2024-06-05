package fr.leboncoin.qa.gherkix;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SimpleSavedRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {


  @Bean
  @Order
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      return http
        .oauth2Login(Customizer.withDefaults())
        .addFilterBefore(new CustomFilter(), UsernamePasswordAuthenticationFilter.class)
        .cors(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable())
        .authorizeRequests(authorize -> authorize
                .requestMatchers(new AntPathRequestMatcher("/**/*.{js,html,css}")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/"),
                        new AntPathRequestMatcher("/ping")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**"),
                        new AntPathRequestMatcher("/api-docs/**")).permitAll()
                .anyRequest().authenticated())
        .build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(List.of("http://localhost:3000",
        "https://my-frontend.com", "https://api-my-backend.com"));

    configuration.setAllowedMethods(List.of("*"));

    configuration.setAllowCredentials(true);

    configuration.setAllowedHeaders(List.of("*"));

    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public RequestCache refererRequestCache() {
    return new HttpSessionRequestCache() {
      @Override
      public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        String referrer = request.getHeader("referer");
        if (referrer != null) {
          request.getSession().setAttribute("SPRING_SECURITY_SAVED_REQUEST",
              new SimpleSavedRequest(referrer));
        }
      }
    };
  }

}
