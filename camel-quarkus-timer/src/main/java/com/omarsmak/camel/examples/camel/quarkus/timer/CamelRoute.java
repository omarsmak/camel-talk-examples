package com.omarsmak.camel.examples.camel.quarkus.timer;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

public class CamelRoute extends EndpointRouteBuilder {

    @Override
    public void configure() throws Exception {
        from(timer("foo").period(1000))
                .log("Hello World From Camel Quarkus");
    }
}
