package top.flyeric.gateway.security.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        return authentication
                .map(auth -> checkAuthorities(auth, authorizationContext.getExchange()))
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    private boolean checkAuthorities(Authentication auth, ServerWebExchange exchange) {
        log.info("[CustomAuthorizationManager] checkAuthorities: {} - {}", auth.getName(), auth.getPrincipal());

        // TODO: 根据具体项目，获取用户的权限列表，进行授权认证


        return true;
    }

}
