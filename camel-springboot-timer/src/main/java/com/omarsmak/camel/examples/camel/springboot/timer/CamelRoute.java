package com.omarsmak.camel.examples.camel.springboot.timer;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends EndpointRouteBuilder {

    @Override
    public void configure() throws Exception {
        from(timer("foo").period(1000))
                .log("Hello World From Camel SpringBoot");
    }
}
