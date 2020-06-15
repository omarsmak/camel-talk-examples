package com.omarsmak.camel.examples.camel.main.timer;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.main.Main;

public class CamelRoute {

    // use Camel Main to setup and run Camel
    private static Main main = new Main();

    private CamelRoute() {
    }

    public static void main(String[] args) throws Exception {

        // add route
        main.configure().addRoutesBuilder(new EndpointRouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(timer("foo").period(1000))
                        .log("Hello World From Camel Main");
            }
        });

        // start camel main
        main.run();
    }
}
