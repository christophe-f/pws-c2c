# Container 2 Container Networking on PWS CloudFoundry

## Requirements
* Pivotal Web Services account 
* Feature c2c enabled on PWS
* Feature SCS (Spring Cloud Services) enabled on PWS
* CloudFoundry CLI
* SpringBoot CLI + Spring Cloud CLI

## Run the example locally

### Service Registry & Circuit Breaker Dashboard
Start both an Eureka & Hystrix Dashboard instances with Spring Cloud CLI
 
    spring cloud eureka hystrixdashboard
    
### Compile and run the example

    # Compiles both services
    $ mvn clean package
    
    # Starts the downstream service
    $ cd travel-service
    $ mvn spring-boot:run
    
    # Starts the client
    $ cd travel-client
    $ mvn spring-boot:run
        
You can start the apps from your IDE or a terminal.

### Test it

Open the url [http://localhost:8761](http://localhost:8761) in a browser, you will see the 2 services registered in Eureka.

Click on the `travel-client` and open the `/destinations` endpoint, you will receive a list of destinations coming from the downstream service `travel-service`.


## Run the example on the PWS CloudFoundry

### Service Registry
On PWS, go to the marketplace and create a `Service Registry` service.

### Circuit Breaker
On PWS, go to the marketplace and create a `Circuit Breaker` service.

### Build and Deploy

    $ mvn clean package
    $ cf push

### Allow c2c communication

Once the 2 applications are deployed and running, you cannot access the `travel-service` from the `travel-client`. You must allow the connection with the following command:

    $ cf add-network-policy travel-client --destination-app travel-service --protocol tcp --port 8080
    
### Test it

In the `Service Registry` service, you will see your 2 registered apps.
 
Try to query the travel-client destinations endpoints. The route is set to generate a random url, so you will have to get it from your environment.

## What's new if you have existing code

### New dependency

You have to add this two new dependencies into your pom.xml file:

    <!-- Replacement for spring-cloud-starter-eureka -->
    <dependency>
        <groupId>io.pivotal.spring.cloud</groupId>
        <artifactId>spring-cloud-services-starter-service-registry</artifactId>
        <version>1.4.1.RELEASE</version>
    </dependency>

    <!-- Replacement for spring-cloud-starter-hystrix -->
    <dependency>
        <groupId>io.pivotal.spring.cloud</groupId>
        <artifactId>spring-cloud-services-starter-circuit-breaker</artifactId>
        <version>1.4.1.RELEASE</version>
    </dependency>

### Direct registration in the properties

To register to SCS Eureka, you have to register with the direct method: 

    spring:
      cloud:
        services:
          registrationMethod: direct

By default Hystrix will not be enabled when using Feign. You can declare a Bean for HystrixFeign.builder() or simply turn it on via the following property: 

    feign:
       hystrix: 
        enabled: true 
          
### Set the `TRUST_CERTS` environment variable

To be able to register to the `Service Registery` service on PWS, you need a using a self-signed SSL certificate. To do so, you have to set a `TRUST_CERTS` environment variable per application. You can use the one in my `manifest.yml` deployment file or the following command line:

    $ cf set-env travel-client TRUST_CERTS api.run.pivotal.io
    $ cf set-env travel-service TRUST_CERTS api.run.pivotal.io

**NOTE**

> Using the Spring Cloud Services Starters for Service Registry will make all application endpoints secured. You should use your own authentication system or disable it like I did in my examples (NOT recommended) with:

    security:
      basic:
        enabled: false

## More Details on my blog
If you want to learn more, check out [my blog](https://medium.com/@christophef/container-networking-with-cloudfoundry-pws-pcf-part1-8840d7f9a985)

## References
Thanks to this [Pivotal blog post](https://content.pivotal.io/blog/building-spring-microservices-with-cloud-foundrys-new-container-networking-stack) that helped me getting started. 
