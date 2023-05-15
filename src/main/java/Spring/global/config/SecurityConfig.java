package Spring.global.config;

import Spring.domain.login.service.CustomUserDetailsService;
import Spring.global.result.ResultCode;
import Spring.global.security.CustomRequestMatcher;
import Spring.global.security.filter.CustomUseridPasswordAuthenticationFilter;
import Spring.global.security.filter.JwtAuthenticationFilter;
import Spring.global.security.filter.ReissueAuthenticationFilter;
import Spring.global.security.handler.CustomAuthenticationEntryPoint;
import Spring.global.security.handler.CustomAuthenticationFailureHandler;
import Spring.global.security.handler.CustomAuthenticationSuccessHandler;
import Spring.global.security.provider.JwtAuthenticationProvider;
import Spring.global.security.provider.ReissueAuthenticationProvider;
import Spring.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST_STATIC = {"/resources/**", "/css/**", "/static/css/**",
            "/static/js/**", "/*.ico"};
    private static final String[] AUTH_WHITELIST = {"/login", "/login/recovery", "/logout/only/cookie", "/signup",
    "/reissue", "/updatepw"};
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService jwtUserDetailService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final ReissueAuthenticationProvider reissueAuthenticationProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPointFailureHandler authenticationEntryPointFailureHandler() {
        return new AuthenticationEntryPointFailureHandler(customAuthenticationEntryPoint);
    }
    @Bean
    public CustomUseridPasswordAuthenticationFilter customUseridPasswordAuthenticationFilter() throws Exception {
        final CustomUseridPasswordAuthenticationFilter filter =
                new CustomUseridPasswordAuthenticationFilter();
        filter.setAuthenticationManager(super.authenticationManager());
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        return filter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        final List<String> skipPaths = new ArrayList<>();
        skipPaths.addAll(Arrays.stream(AUTH_WHITELIST_STATIC).collect(Collectors.toList()));
        skipPaths.addAll(Arrays.stream(AUTH_WHITELIST).collect(Collectors.toList()));
        final RequestMatcher matcher = new CustomRequestMatcher(skipPaths);
        final JwtAuthenticationFilter filter = new JwtAuthenticationFilter(matcher, jwtUtil);

        filter.setAuthenticationManager(super.authenticationManager());
        filter.setAuthenticationFailureHandler(authenticationEntryPointFailureHandler());
        return filter;
    }

    @Bean
    public ReissueAuthenticationFilter reissueAuthenticationFilter() throws Exception {
        final ReissueAuthenticationFilter filter = new ReissueAuthenticationFilter();
        filter.setAuthenticationManager(super.authenticationManager());
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        return filter;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(jwtUserDetailService);
        return daoAuthenticationProvider;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider)
                .authenticationProvider(reissueAuthenticationProvider)
                .authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AUTH_WHITELIST_STATIC);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        configureCustomBeans();

        http.exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint);
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest)
                .permitAll()
                .antMatchers(AUTH_WHITELIST_STATIC)
                .permitAll()
                .antMatchers(AUTH_WHITELIST)
                .permitAll()
                .anyRequest().hasAuthority("USER");

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(customUseridPasswordAuthenticationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(reissueAuthenticationFilter(), JwtAuthenticationFilter.class);

    }

    private void configureCustomBeans() {
        final Map<String, ResultCode> map = new HashMap<>();
        map.put("/login", ResultCode.LOGIN_SUCCESS);
        map.put("/reissue", ResultCode.REISSUE_SUCCESS);
        map.put("/login/recovery", ResultCode.LOGIN_WITH_CODE_SUCCESS);
        customAuthenticationSuccessHandler.setResultCodeMap(map);
    }
}
