package com.portfolio.apigatewayservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    // application.yml 에서 데이터를 가져오기 위한 용도
    Environment env;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    // login -> token -> users(with token) -> header(includes token)
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // 포함이 안되어 있다면
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            // bearer token, header 에서 가져오기
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");

            if (!isJwtValid(jwt)) { // validate JWT token
                return onError(exchange, "JWT Token is not valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    // Mono, Flux -> Spring WebFlux
    // 단일 값이면 mono 를 사용하고, 여러가지일 경우 flux
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        // 기존 스프링 MVC 를 사용했다고 하면 ServletResponse 객체를 사용했지만
        // webFlux 에서는 Servlet 개념을 사용하지 않는다. 
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(err);
        return response.setComplete();
    }
// java.lang.ClassNotFoundException: javax.xml.bind.DatatypeConverter
    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;

        Claims claims = null;
        try {

            claims = Jwts.parser()
                    .setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(jwt)
                    .getBody();

            String user_role = claims.get("user_role", String.class);
            if (user_role.equals("ROLE_UNAUTHORIZED")) {
                throw new IllegalStateException("NOT AUTHORISED");
            }
        } catch (Exception e) {
            returnValue = false;
        }

        if (claims == null || claims.isEmpty()) {
            returnValue = false;
        }

        return returnValue;
    }

    public static class Config {

    }

}
