package top.flyeric.auth.infrastructure.config.security.userdetails;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.flyeric.auth.infrastructure.user.dataobject.UserDO;
import top.flyeric.auth.infrastructure.user.dataobject.UserDOExample;
import top.flyeric.auth.infrastructure.user.mapper.UserDOMapper;

import java.util.Map;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDOMapper userDOMapper;
    private final ObjectMapper objectMapper;

    public CustomUserDetailsService(UserDOMapper userDOMapper, ObjectMapper objectMapper) {
        this.userDOMapper = userDOMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("[CustomUserDetailsService] loadUserByUsername {}", username);
        UserDO userDO = this.getUserByUsername(username);
        return CustomUserDetails.builder()
                .userId(userDO.getId())
                .username(userDO.getUsername())
                .password(userDO.getPassword())
                .userType(userDO.getUserType())
                .build();
    }

    private UserDO getUserByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            throw new RuntimeException("username 不可为空");
        }

        UserDO userDO = userDOMapper.selectOneByExample(
                UserDOExample.newAndCreateCriteria()
                        .andUsernameEqualTo(username)
                        .example());
        if (userDO == null) {
            throw new RuntimeException("User not found, username=" + username);
        }
        return userDO;
    }

    public Map<String, Object> getUserInfoMap(String username) {
        log.info("[CustomUserDetailsService] getUserInfoMap {}", username);
        UserDO userDO = this.getUserByUsername(username);
        Map<String, Object> userInfo = objectMapper.convertValue(userDO, new TypeReference<Map<String, Object>>() {});
        userInfo.put("sub", userDO.getUsername());
        return userInfo;
    }

}
