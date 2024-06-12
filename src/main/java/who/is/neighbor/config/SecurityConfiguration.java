package who.is.neighbor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeHttpRequests((authorize) -> {
                        authorize.requestMatchers("/accounts", "/accounts/login").permitAll();
                        authorize.anyRequest().authenticated();
                    }
                )
                .formLogin(withDefaults())
                .sessionManagement((sessions) -> sessions
                        .sessionConcurrency((concurrency) -> concurrency
                                .maximumSessions(1)
                                .expiredUrl("/login?expired")
                        )
                );

        return http.build();
    }
}
