package com.gamebazaar.gamebazaarserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication(scanBasePackages = "com.gamebazaar.gamebazaarserver", exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class GameBazaarServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameBazaarServerApplication.class, args);
	}

}
