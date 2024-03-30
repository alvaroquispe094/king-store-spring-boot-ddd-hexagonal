package com.groupal.king.store.adapter.security;

import com.groupal.king.store.adapter.security.jwt.AuthEntryPointJwt;
import com.groupal.king.store.adapter.security.jwt.AuthTokenFilter;
import com.groupal.king.store.adapter.security.jwt.DeniedEntryPointJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final AuthenticationAdapter userDetailsService;

    private final AuthEntryPointJwt unauthorizedHandler;

    private final DeniedEntryPointJwt forbiddenHandler;

    private final AuthTokenFilter authTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    private static final String[] WHITE_LIST_URL = {
            "/api-docs",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui/index.html"
    };

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(corsCustomiser())
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(req -> req
                    .requestMatchers(WHITE_LIST_URL)
                    .permitAll()
                    //PUBLIC
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .requestMatchers(HttpMethod.GET,
                            "/api/v1/catalog/products",
                            "/api/v1/catalog/products/**",
                            "/api/v1/catalog/categories"
                    )
                    .permitAll()

                    //PRIVATE
                    .requestMatchers("/api/v1/users/**")
                    .hasAnyAuthority("ROLE_MODERATOR", "ROLE_ADMIN")

                    .requestMatchers("/api/v1/mod/**")
                    .hasAnyAuthority("ROLE_MODERATOR", "ROLE_ADMIN")

                    .requestMatchers("/api/v1/catalog/**")
                    .hasAuthority("ROLE_ADMIN")

                    .anyRequest()
                    .authenticated()

            )
            .exceptionHandling(exception -> exception.accessDeniedHandler(forbiddenHandler));

        return http.build();
    }

    private Customizer<CorsConfigurer<HttpSecurity>> corsCustomiser() {
        return t -> t.configurationSource(getCorsConfiguration());
    }

    private CorsConfigurationSource getCorsConfiguration() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("http://localhost:4200"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
