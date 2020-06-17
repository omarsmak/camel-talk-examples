# Camel Quarkus Timer Example
This example utlizes [Camel Quarkus](https://github.com/apache/camel-quarkus) core component. 

First, you need to follow the steps [here](https://camel.apache.org/camel-quarkus/latest/user-guide/first-steps.html#_prerequisites) in order to setup GraalVM correctly.

To build
```
 ../mvnw clean package
```

To run:
```
java -jar target/camel-quarkus-timer-1.0-SNAPSHOT-runner.jar 
```

To build it natively using `native-image`, make sure you have GraaVM setup correctly and build using the following maven profile:
```
 ../mvnw clean package -Pnative
```

To run the native compiled route:
```
./target/camel-quarkus-timer-1.0-SNAPSHOT-runner 
```