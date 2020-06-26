// from: https://github.com/apache/camel-k/blob/master/examples/routes-rest.js
// To run: kamel run --name=withrest routes-rest.js 

rest('/say/hello')
    .produces("text/plain")
    .get()
        .route()
        .transform().constant("Hello World");