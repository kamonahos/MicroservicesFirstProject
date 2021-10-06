package com.microservices.apigateway;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){
//ean ena request erthei gia /get kane redirect sto httpbin...px
        return builder.routes()
                .route(p ->p
                        .path("/get")
                .filters(f -> f
                        .addRequestHeader("MyHeader","MyURI")
                        .addRequestParameter("Param","Myvalue"))
                .uri("http://httpbin.org:80"))
                //edw kanw redirect oti erthei me currency-exchange kai otidhpote meta sto apokatw kai lb apo ktw gia load balance
                .route(p->p.path("/currency-exchange/**")
                        .uri("lb://currency-exchange"))
                .route(p->p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p->p.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion"))
                .route(p->p.path("/currency-conversion-new/**")
                        //ayto metatrepei ta URI kai to segment einai gia na tou peis oti edw exei segment???
                        .filters(f-> f.rewritePath("/currency-conversion-new/(?<segment>.*)",
                                "/currency-conversion-feign/${segment}"))
                        .uri("lb://currency-conversion"))
                .build();

    }
}
