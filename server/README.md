# chicken-cup project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

## Application API

Application API is exposed using [swagger api](https://quarkus.io/guides/openapi-swaggerui), you can see your API [here](http://localhost:8080/q/swagger-ui)
and you can download your API [here](http://localhost:8080/q/openapi) 

## Health check

Health check is made with [microprofile](https://quarkus.io/guides/microprofile-health) it's available [here](http://localhost:8080/q/health) 

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `chicken-cup-1.0-SNAPSHOT-runner.jar` file in the `/target` directory. Be aware that it’s not an _
über-jar_ as the dependencies are copied into the `target/lib` directory.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/chicken-cup-1.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/chicken-cup-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html
.
