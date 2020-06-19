package com.omarsmak.camel.examples.camel.quarkus.rest;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

public class CamelRoute extends EndpointRouteBuilder {
    @Override
    public void configure() throws Exception {
        rest()
                .get("/hello?q={text}")
                .produces("text/plain")
                .route()
                .setBody(simple("Text is: ${headers.q}"))
                .endRest();
    }
}
