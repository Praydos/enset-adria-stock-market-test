//package org.example.chatbotservice.config;
//
//
//
//import org.example.chatbotservice.mcp.tools.StockMarketTools;
//import org.springframework.ai.tool.ToolCallbackProvider;
//import org.springframework.ai.tool.method.MethodToolCallbackProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/api/stock/**").authenticated()
//                        .anyRequest().permitAll()
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter()))
//                );
//        return http.build();
//    }
//    @Bean
//    public ToolCallbackProvider stockTools(StockMarketTools stockMarketTools) {
//        return MethodToolCallbackProvider.builder()
//                .toolObjects(stockMarketTools) // Use the Spring bean here
//                .build();
//    }
//}