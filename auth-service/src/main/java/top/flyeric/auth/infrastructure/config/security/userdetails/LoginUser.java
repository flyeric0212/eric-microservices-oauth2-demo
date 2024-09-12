package top.flyeric.auth.infrastructure.config.security.userdetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {

    private Integer userId;
    private String username;
    private Integer userType;
}
