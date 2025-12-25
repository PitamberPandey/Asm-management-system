package com.amsmanagament.system;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Api for asm Mangagement System",
                version = "1.0",
                description = "Swagger UI integration with Spring Boot & Security"
        )
)
@SecurityScheme( name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT" )
public class AmsmanagamentSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmsmanagamentSystemApplication.class, args);
	}

}
