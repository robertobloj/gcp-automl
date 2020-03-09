# Cloud Run

Before go further read comparison between `Cloud Function` and `Cloud Run`:

* [comparison 1](https://medium.com/google-cloud/cloud-run-and-cloud-function-what-i-use-and-why-12bb5d3798e1)
* [comparison 2](https://medium.com/google-cloud/cloud-run-vs-cloud-functions-whats-the-lowest-cost-728d59345a2e)
* [serverless options](https://cloud.google.com/serverless-options)

## Create lambda with example image

1. [Deploy] example image:

```
gcloud run deploy CLOUD_RUN_NAME --image gcr.io/cloudrun/hello \
    --platform=managed --region=us-central1 --allow-unauthenticated
```

2. Read output logs and find url for your app:

```console
Deploying container to Cloud Run service [CLOUD_RUN_NAME] in project [PROJECT_ID] region [us-central1]
Deploying new service...
  Setting IAM Policy...done
  Creating Revision...done
  Routing traffic...done
Done.
Service [CLOUD_RUN_NAME] revision [CLOUD_RUN_NAME-00001-kad] has been deployed \ 
and is serving 100 percent of traffic at https://CLOUD_RUN_NAME-qx2d4oqzga-uc.a.run.app
```

Alternatively go to [GCP console] and check url for your lambda.

3. [Undeploy] service:

```
gcloud run services delete CLOUD_RUN_NAME --platform=managed --region=us-central1
```

## Build and deploy image in Cloud Run

1. We configure proxy:

```
SET HTTPS_PROXY=...
SET HTTP_PROXY=...
SET NO_PROXY=*...
```

2. We download simple web app (based on springboot):

```
curl https://start.spring.io/starter.zip \
    -d dependencies=web \
    -d javaVersion=1.8 \
    -d bootVersion=2.1.3.RELEASE \
    -d name=helloworld \
    -d artifactId=helloworld \
    -d baseDir=helloworld \
    -o helloworld.zip
unzip helloworld.zip
cd helloworld
```

3. Edit `src/main/java/com/example/helloworld/HelloworldApplication.java` and add simple endpoint to application:

```java
package com.example.helloworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class HelloworldApplication {

        @Value("${TARGET:World}")
        String target;

        @RestController
        class HelloworldController {
                @GetMapping("/")
                String hello() {
                        return "Hello " + target + "!";
                }
        }

        public static void main(String[] args) {
                SpringApplication.run(HelloworldApplication.class, args);
        }
}
```

## Containerizing an app and uploading it to Container Registry

1. Create Dockerfile

```Docker
# Use the official maven/Java 8 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.5-jdk-8-alpine as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package -DskipTests

# Use AdoptOpenJDK for base image.
# It's important to use OpenJDK 8u191 or above that has container support enabled.
# https://hub.docker.com/r/adoptopenjdk/openjdk8
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds
FROM adoptopenjdk/openjdk8:jdk8u202-b08-alpine-slim

# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/target/helloworld-*.jar /helloworld.jar

# Run the web service on container startup.
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=${PORT}","-jar","/helloworld.jar"]
```

2. [Build image]:

```
gcloud builds submit --tag gcr.io/PROJECT_ID/helloworld
```

4. Check [images list]:

```
gcloud container images list --repository=gcr.io/PROJECT_ID
```

3. [Deploy] image:

```
gcloud run deploy CLOUD_RUN_NAME --image gcr.io/PROJECT_ID/helloworld \
    --platform=managed --region=us-central1 --allow-unauthenticated
```

4. Test application
5. [Undeploy] service:

```
gcloud -q run services delete CLOUD_RUN_NAME --platform=managed --region=us-central1
```

[Images list]: https://cloud.google.com/sdk/gcloud/reference/container/images/list
[Deploy]: https://cloud.google.com/sdk/gcloud/reference/run/deploy
[Undeploy]: https://cloud.google.com/sdk/gcloud/reference/run/services/delete
[GCP console]: https://console.cloud.google.com/run
[Build image]: https://cloud.google.com/sdk/gcloud/reference/builds/submit
