package server.mainproject.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import server.mainproject.auth.Oauth.OAuth2Service;
import server.mainproject.auth.filter.JwtAuthenticationFilter;
import server.mainproject.auth.filter.JwtVerificationFilter;
import server.mainproject.auth.handler.MemberAuthenticationFailureHandler;
import server.mainproject.auth.handler.MemberAuthenticationSuccessHandler;
import server.mainproject.auth.jwt.JwtTokenizer;
import server.mainproject.auth.utils.CustomAuthorityUtils;
import server.mainproject.auth.utils.JwtUtils;

import java.util.Arrays;

@Configuration
//@EnableWebSecurity(debug = false)
@RequiredArgsConstructor
public class SecurityConfiguration implements WebMvcConfigurer {
    private final CustomAuthorityUtils authorityUtils;
    private final OAuth2Service oAuth2Service;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors(httpSecurityCorsConfigurer -> corsConfigurationSource())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().permitAll()
//                .formLogin().disable()
//                .httpBasic().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())  // 추가
//                .accessDeniedHandler(new MemberAuthenticationDeniedHandler())            // 추가
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers(HttpMethod.POST, "/*/members").permitAll()
                        .antMatchers(HttpMethod.POST, "/*/login").permitAll()
                        .antMatchers(HttpMethod.PATCH, "/*/members/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/*/members").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET, "/*/members/**").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/*/members/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/*/posts").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/*/posts/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/*/posts").permitAll()
                        .antMatchers(HttpMethod.GET, "/*/posts/**").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/*/posts/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/*/comments").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/*/comments/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/*/comments/**").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/*/comments/**").hasRole("USER")
                        .anyRequest().permitAll()
                )
                .oauth2Login() // OAuth2를 통한 로그인 사용
               .defaultSuccessUrl("/", true) // 로그인 성공시 이동할 URL
                .failureUrl("/login")
//                .loginPage("/oauth/login")
                .userInfoEndpoint() // 사용자가 로그인에 성공하였을 경우,
                .userService(oAuth2Service); // 해당 서비스 로직을 타도록 설정
//                .oauth2Login(login -> login
//                        .clientRegistrationRepository(clientRegistrationRepository())
//                        .authorizedClientService(authorizedClientService())
//                        .loginPage("/login")
//                );



        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "PUT", "HEAD", "OPTIONS"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Refresh"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);


            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer());
            jwtAuthenticationFilter.setFilterProcessesUrl("auth/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtUtils(), authorityUtils);

            builder.addFilter(jwtAuthenticationFilter).addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }


    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils(jwtTokenizer());
    }

    @Bean
    public JwtTokenizer jwtTokenizer() {
        return new JwtTokenizer();
    }

}