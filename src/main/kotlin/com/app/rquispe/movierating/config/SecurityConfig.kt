package com.app.rquispe.movierating.config

import com.app.rquispe.movierating.model.User
import com.app.rquispe.movierating.security.MovieReactiveUserDetailService
import com.app.rquispe.movierating.service.UserService
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {


//    “The preceding configuration uses @EnableWebFluxSecurity to configure the web filters necessary for Spring Security
//    Reactive and override any auto-configurations. The securityWebFilterChain function uses its ServerHttpSecurity
//    argument to protect all the endpoint sets under /movies/** and ensure they're accessible only after authentication.

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity) : SecurityWebFilterChain {
        http.authorizeExchange()
            .pathMatchers("/movies/**")
            .authenticated()
            .and()
            .httpBasic()
            .and()
            .csrf()
            .disable();
        return http.build();
    }

    @Bean
    fun authenticationManager(movieeReactiveUserDetailsService: MovieReactiveUserDetailService) :
            UserDetailsRepositoryReactiveAuthenticationManager {

        val userDetailsRepositoryReactiveAuthenticationManager = UserDetailsRepositoryReactiveAuthenticationManager(movieeReactiveUserDetailsService)
        userDetailsRepositoryReactiveAuthenticationManager.setPasswordEncoder(passwordEncoder())

        return userDetailsRepositoryReactiveAuthenticationManager

    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Profile("default")
    fun applicationRunner(userService : UserService): ApplicationRunner {
        return ApplicationRunner {
            userService.save(User(1, "user", passwordEncoder().encode("password"), "USER", "User of Moviee")).subscribe();
            userService.save(User(2, "admin", passwordEncoder().encode("password"), "ADMIN", "Admin of Moviee")).subscribe()
        }
    }
}
