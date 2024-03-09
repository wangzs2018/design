package com.book;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
@EnableApolloConfig
public class DesignApplication {
	public static void main(String[] args) {
		System.out.println(SpringBootVersion.getVersion());
		SpringApplication.run(DesignApplication.class, args);
	}
}
