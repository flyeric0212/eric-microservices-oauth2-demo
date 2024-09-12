package top.flyeric.auth.infrastructure.user.dataobject;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDO {
    private Integer id;

    private String username;

    private String password;

    private Integer userType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}