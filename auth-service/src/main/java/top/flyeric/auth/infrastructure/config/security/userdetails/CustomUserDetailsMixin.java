package top.flyeric.auth.infrastructure.config.security.userdetails;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class CustomUserDetailsMixin {
}
