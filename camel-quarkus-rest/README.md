# Camel Quarkus Rest Example
This example utlizes [Camel Quarkus](https://github.com/apache/camel-quarkus) core component. 

First, you need to follow the steps [here](https://camel.apache.org/camel-quarkus/latest/user-guide/first-steps.html#_prerequisites) in order to setup GraalVM correctly.

To build
```
 ../mvnw clean package
```

To run:
```
java -jar target/camel-quarkus-rest-1.0-SNAPSHOT-runner.jar 
```

To build it natively using `native-image`, make sure you have GraaVM setup correctly and build using the following maven profile:
```
 ../mvnw clean package -Pnative
```

To run the native compiled route:
```
./target/camel-quarkus-rest-1.0-SNAPSHOT-runner 
```

## Deploy to Knative
In this example, we will deploy our application to Knative cluster on K8s. We will need to follow these steps:

1.  You will need to install Knative with your preferred ingress gateway following the instructions [here](https://knative.dev/docs/install/any-kubernetes-cluster/).
1.  We will use the native executable to be used for our Docker image, however we will need to tell Quarkus maven plugin that we are targeting Docker environment. To do so, we need to execute the following command:
`
../mvnw package -Pnative -Dquarkus.native.container-runtime=docker
` .
1. Once the build is done, we will need to build our Docker container via this command `docker build -f Dockerfile.native -t {USERNAME}/camel-quarkus-rest .`. You will need to change `{USERNAME}` with your Docker hub username.
1. Push the generated Docker image to your Docker hub via this command `docker push {USERNAME}/camel-quarkus-rest`. Remember to change 
`{USERNAME}` with your Docker hub username.
1. In `service.yaml`, replace `{USERNAME}` with your Docker hub user name and then run `kubectl apply --filename service.yaml`.
1. Assuming everything went well, run this command `kubectl get ksvc` which will show the URLs for your services that you can use to execute the service. The URL will be in this form: 
```
http://camel-quarkus-rest.default.example.com
``` 
which is this form `{APPLICATION_NAME}.{NAMESPACE}.{CUSTOM_DOMAIN}`, to access it, you will need setup your domain to route all traffic through your ingress Gateway. 