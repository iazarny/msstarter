package com.az.ms;

/**
 * Created by Igor_Azarny on 12/14/2017.
 */


import com.fasterxml.classmate.TypeResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
@EnableSwagger2
@Import({springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class})

public class SwaggerConfig {

    private static final String OAUTH = "OAuth";
    @Autowired
    private TypeResolver typeResolver;

    /*@Value("${security.oauth2.client.accessTokenUri}")
    private String tokenUrl;

    @Value("${security.oauth2.client.userAuthorizationUri}")
    private String tokenRequestUrl;

    @Value("${security.oauth2.client.clientId}")
    private String clientId;

    @Value("${security.oauth2.client.clientSecret}")
    private String clientSecret;*/

    @Bean
    public Docket api() {
        final Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.az.ms"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, java.util.Date.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false)
                //.securitySchemes( Stream.of(oAuthSchema()).collect(Collectors.toList())    )
                //.securityContexts(   Stream.of(securityContext()).collect(Collectors.toList())    )
                .enableUrlTemplating(false);
        docket.ignoredParameterTypes(Pageable.class, PagedResourcesAssembler.class);
        return docket;
    }


    @SuppressWarnings("squid:S1313")
    private ApiInfo apiInfo() {
        Contact contact = new Contact(
                "Igor Azarny",
                "https://github.com/iazarny/msstarter", "iazarny@yahoo.com");

        return new ApiInfo(
                "Microservice starter project",
                "For front-end and external systems",
                "1.0.0.1",
                "https://github.com/iazarny/msstarter",
                contact,
                "Apache License",
                "https://www.apache.org/licenses/LICENSE-2.0",
                Collections.EMPTY_LIST);
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return  Stream.of(new SecurityReference(OAUTH, authorizationScopes)).collect(Collectors.toList());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    /*private OAuth oAuthSchema() {
        List<AuthorizationScope> scopes = ImmutableList.<AuthorizationScope>builder().add(
                new AuthorizationScope("read", "for read operations only"),
                new AuthorizationScope("write", "for write operations only")
        ).build();
        AuthorizationCodeGrant authorizationCodeGrant = new AuthorizationCodeGrantBuilder()
                .tokenEndpoint(new TokenEndpoint(tokenUrl, "Bearer"))
                .tokenRequestEndpoint(new TokenRequestEndpoint(tokenRequestUrl, clientId, clientSecret))
                .build();
        List<GrantType> grantTypes = ImmutableList.<GrantType>builder().add(
                //new ResourceOwnerPasswordCredentialsGrant(tokenUrl),
                authorizationCodeGrant).build();
        return new OAuthBuilder().grantTypes(grantTypes).name(OAUTH).scopes(scopes).build();
    }*/


    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
                "client",
                "secret",
                "client-realm",
                "msstarter",
                OAUTH,
                ApiKeyVehicle.HEADER,
                "Bearer",
                " " /*scope separator*/);
    }

    /**
     * Allows cross origin for testing swagger docs using swagger-ui from local file system
     */
    @Component
    public static class CrossOriginFilter implements Filter {

        private static final Logger log = LoggerFactory.getLogger(CrossOriginFilter.class);

        //@Value("http://${authserver.hostname}:${authserver.port}")
        //private String authServerUrl;

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            //Do nothing
        }

        @Override
        public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
                ServletException {

            log.debug("Applying CORS filter");
            HttpServletResponse response = (HttpServletResponse) resp;
            response.setHeader("Access-Control-Allow-Origin", /*authServerUrl*/"*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "0");
            chain.doFilter(req, resp);
        }

        @Override
        public void destroy() {
            //Do nothing
        }
    }


}
