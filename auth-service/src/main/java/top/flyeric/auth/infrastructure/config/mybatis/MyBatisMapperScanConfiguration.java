package top.flyeric.auth.infrastructure.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"top.flyeric.auth.infrastructure.**.mapper"})
public class MyBatisMapperScanConfiguration {
}
