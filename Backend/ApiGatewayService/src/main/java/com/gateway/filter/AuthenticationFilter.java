package com.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.gateway.exception.UserAccessDeniedException;
import com.gateway.util.JwtUtil;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();

            if (isSecured(path)) {
                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new RuntimeException("Missing or invalid Authorization header");
                }

                String token = authHeader.substring(7); 
                String role = jwtUtil.extractRole(token);

                System.out.println(role);
                System.out.println(path);
                if (path.startsWith("/admin") && !role.equals("ADMIN")) {
                    throw new UserAccessDeniedException("Forbidden - Admin role required");
                } else if (path.startsWith("/user") && !role.equals("USER")) {
                    throw new UserAccessDeniedException("Forbidden - User role required");
                }

                if (path.startsWith("/user") && role.equals("ADMIN")) {
                    throw new UserAccessDeniedException("Forbidden - Admin role cannot access /user path");
                }
            }

            return chain.filter(exchange);
        };
    }

    private boolean isSecured(String path) {
        return !path.startsWith("/auth");
    }

    public static class Config {
       
    }
}
