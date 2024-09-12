package top.flyeric.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;
import top.flyeric.gateway.security.authorization.CustomAuthorizationManager;
import top.flyeric.gateway.security.util.RSAKeyPairUtil;

import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebFluxSecurity
public class WebFluxSecurityConfig {

    public static final String[] IGNORE_URLS = {
            "/public/**",
            "api/auth-service/oauth2/**",
    };

    private final CustomAuthorizationManager customAuthorizationManager;

    public WebFluxSecurityConfig(CustomAuthorizationManager customAuthorizationManager) {
        this.customAuthorizationManager = customAuthorizationManager;
    }

    /**
     * 配置认证相关的过滤器链
     */
    @Bean
    public SecurityWebFilterChain defaultSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .authorizeExchange(authorize -> authorize
                        .pathMatchers(IGNORE_URLS).permitAll()
                        .anyExchange().access(customAuthorizationManager)
                )
                .oauth2ResourceServer((resourceServer) -> resourceServer
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(grantedAuthoritiesExtractor())
                                .jwtDecoder(jwtDecoder())
                        )
                )
                .build();
    }

    /**
     * 自定义jwt解析器，设置解析出来的权限信息的前缀与在jwt中的key
     */
    public Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // 设置解析权限信息的前缀，设置为空是去掉前缀
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        // 设置权限信息在jwt claims中的key
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

    public NimbusReactiveJwtDecoder jwtDecoder() {
        try {
            RSAPublicKey publicKey = (RSAPublicKey) RSAKeyPairUtil.loadPublicKey();

            return NimbusReactiveJwtDecoder.withPublicKey(publicKey).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
