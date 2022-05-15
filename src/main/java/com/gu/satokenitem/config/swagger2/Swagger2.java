package com.gu.satokenitem.config.swagger2;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class Swagger2 {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gu.satokenitem"))
                .paths(PathSelectors.any())
                .build();

    }

    @Bean
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("SaToken API")
                .description("swagger-bootstrap-ui")
                .termsOfServiceUrl("javascript:void(0)")
                .version("1.0")
                .build();

    }
}
