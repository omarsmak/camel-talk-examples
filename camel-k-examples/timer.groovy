// To run: kamel run timer.groovy

from('timer:foo?period=1000')
    .log('Hello World from Camel K')