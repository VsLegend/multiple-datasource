package com.example.multipledatasource.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

/**
 * open api文档配置
 * 访问地址 <a href="http://localhost:8080/doc.html#/home">open api</a>
 *
 * @author Wang Junwei
 * @date 2022/7/1 11:24
 */

//@Profile(value = {"local", "dev", "test"})
@Configuration
public class OpenApiConfig {

    @Value("${springdoc.swagger-ui.url:}")
    private String url;

    @Bean
    public OpenAPI api() {
        OpenAPI openapi = new OpenAPI().info(new Info().title("API文档")
                .description("API文档")
                .version("v1.0.0"));
        if (!ObjectUtils.isEmpty(url)) {
            Server server = new Server();
            server.setUrl(url);
            openapi.addServersItem(server);
        }
        return openapi;
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("PUBLIC")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> operation.addParametersItem(
                new Parameter()
                        .in("header")
                        .required(true)
                        .description("token 验证")
                        .name("token"));
    }

}
