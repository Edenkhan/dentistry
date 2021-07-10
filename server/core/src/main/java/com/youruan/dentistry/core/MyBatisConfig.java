package com.youruan.dentistry.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.youruan.dentistry.core.*.mapper")
public class MyBatisConfig {

}
