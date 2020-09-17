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

To build a docker image with native binaries, just run:
```
docker build -f Dockerfile.multistage -t camel-quarkus-rest-native .   
```

And run it with this command:
```
docker run -i --rm -p 8080:8080 camel-quarkus-rest-native
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
1. In `service.yaml`, replace `__IMAGE__` with your Docker hub image url, e.g: `index.docker.io/omarsmak/camel-quarkus-rest` and then run `kubectl apply --filename service.yaml`.
1. Assuming everything went well, run this command `kubectl get ksvc` which will show the URLs for your services that you can use to execute the service. The URL will be in this form: 
```
http://camel-quarkus-rest.default.example.com
``` 
which is this form `{APPLICATION_NAME}.{NAMESPACE}.{CUSTOM_DOMAIN}`, to access it, you will need setup your domain to route all traffic through your ingress Gateway. 

## Build with Tekton and deploy to Knative 
**Note**: You will need to have nodes with at least *8GB* in your cluster due to Graal Native Image memory consumption. 

We can setup an automated pipeline in Tekton to build our application and deploy it to knative cluster, the pipeline consists of the following steps:
1. Build our native application, create an image for it and push it to Dockerhub.
2. Prepare our built application for deployment.
3. Deploy our application to default namespace with knative service enabled.


To do this, we need to follow these steps:
1. Install Tekton in your K8s cluster following the steps [here](https://github.com/tektoncd/pipeline/blob/master/docs/install.md).
1. Install [Tekton CLI](https://github.com/tektoncd/cli) or [Tekton dashboard](https://github.com/tektoncd/dashboard) to be able to monitor the tasks.
1. Run this command to prepare the namespace:
`
kubectl apply -f tekton/namespace.yaml
` 
1. In `tekton/account.yaml`, replace both `{YOUR_DOCKER_USERNAME}` and `{YOUR_DOCKER_PASSWORD}` with your Docker credentials and run the command `kubectl apply -f tekton/account.yaml`. This will create a ServiceAccount with limited permissions ClusterRole, **hence if you concern about the assigned roles, you may need to change that.**
1. Run `kubectl apply -f tekton/task.yaml`. This file contains our Task blueprint and the steps on how our task will run later.
1. In `tekton/taskrun.yaml`, change `{DOCKER_USERNAME}` with your Docker username and let's run our task using this command `kubectl apply -f tekton/taskrun.yaml`.
1. To monitor our task, we can run this command `tkn taskrun describe --namespace camel-quarkus-rest-run-1`.
1. Once the Tekton task has succeeded, our application should be deployed in Knative, we can run this command `kubectl get ksvc` which will show the URLs for your deployed services that you can use to execute the service. The URL will be in this form: 
```
http://camel-quarkus-rest.default.example.com
``` 
which is this form `{APPLICATION_NAME}.{NAMESPACE}.{CUSTOM_DOMAIN}`, to access it, you will need setup your domain to route all traffic through your ingress Gateway.

To clean up everything:
1. `kubectl delete kservice camel-quarkus-rest`.
1. `kubectl delete namespace ci-pipeline`.

## Bonus: Deploy Knative in Azure Kubernetes Cluster
In this [blog](https://blog.oalsafi.com/post/deploy-camel-quarkus-to-azure-knative/) written by me, we took a detailed look on how to install Knative in Azure Kubernetes Cluster (AKS) and how to configure it. 