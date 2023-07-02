package com.task.schoolservis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiKey apiKey() {
        return new ApiKey( "JWT", AUTHORIZATION_HEADER, "header" );
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "School Service APIs",
                "School Service APIs Documentation",
                "1",
                "Terms of service",
                new Contact( "Kevin Halili", "https://github.com/kevinHalili/Laconics-Task/tree/main/school-servis", "kevin_halili@outlook.com" ),
                "License of API",
                "API license URL",
                Collections.emptyList()
        );
    }

    @Bean
    public Docket api() {
        return new Docket( DocumentationType.SWAGGER_2 )
                .apiInfo( this.apiInfo() )
                .securityContexts( Collections.singletonList( this.securityContext() ) )
                .securitySchemes( List.of( this.apiKey() ) )
                .select()
                .apis( RequestHandlerSelectors.any() )
                .paths( PathSelectors.any() )
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences( this.defaultAuth() ).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope( "global", "accessEverything" );

        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        return List.of( new SecurityReference( "JWT", authorizationScopes ) );
    }
}
