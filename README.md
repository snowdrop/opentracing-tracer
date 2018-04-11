# opentracing-tracer
Spring Boot autoconfiguration for OpenTracing tracer implementations

** WARNING **

This repo has been deprecated. Please use [this](https://github.com/opentracing-contrib/java-spring-cloud/tree/master/opentracing-spring-cloud-starter-jaeger) module instead

## Usage

Depending on what kind of Spring project is to be instrumented, add of one the following dependencies

* Spring MVC project 
```xml
<dependency>
    <groupId>me.snowdrop</groupId>
    <artifactId>opentracing-tracer-jaeger-spring-web-starter</artifactId>
    <version>0.1.0</version>
</dependency>
```


* Spring Cloud project
```xml
<dependency>
    <groupId>me.snowdrop</groupId>
    <artifactId>opentracing-tracer-jaeger-spring-cloud-starter</artifactId>
    <version>0.1.0</version>
</dependency>
```

If for some reason the user plans to include the instrumentation dependencies manually, then the 
following starter can be used to simply include the auto-configuration code and Jaeger dependency

```xml
<dependency>
    <groupId>me.snowdrop</groupId>
    <version>0.1.0</version>
    <artifactId>opentracing-tracer-jaeger-starter</artifactId>
</dependency>
``` 

## Configuration options

All the available configuration options can be seen in `me.snowdrop.opentracing.tracer.JaegerConfigurationProperties`.
The prefix to be used for these properties is `opentracing.jaeger`.

Beware to use the correct syntax for properties that are camel-case in `JaegerConfigurationProperties`.

* For properties / yaml files use `-`. For example `opentracing.jaeger.log-spans=true`
* For environment variables use `_`. For example `OPENTRACING_JAEGER_LOG_SPANS` 

## Defaults

If no configuration options are changed and the user does not manually provide any of the beans that the 
auto-configuration process provides, the following defaults are used:

* `spring-boot` Will be used as the service-name
* A `CompositeReporter` is provided which does not contain any delegates - effectively functioning as a Noop `Reporter`
* A `ConstSampler` with the value of `true`. This means that every trace will be sampled
* A `NullStatsReporter` is used - effectively meaning that no stats will be collected about the reporting of traces

## Manual bean provisioning

Any of the following beans can be provided by the application (by adding configuring them as bean with `@Bean` for example)
and will be used to by the Tracer instead of the auto-configured beans.

* `com.uber.jaeger.samplers.Sampler`  
* `com.uber.jaeger.metrics.StatsReporter`  
* `com.uber.jaeger.metrics.Metrics`  
* `com.uber.jaeger.reporters.Reporter` 

## Common cases

### Set service name 

Set `opentracing.jaeger.service-name` to the desired name

### Define an HTTP collector

Set `opentracing.jaeger.http-sender.url` to the URL of the Jaeger collector

### Define a UDP collector

Set `opentracing.jaeger.udp-sender.host` to the host of the Jaeger collector
and `opentracing.jaeger.udp-sender.port` to the end of the Jaeger collector

### Enable logging of spans

Set `opentracing.jaeger.log-spans` to `true`

### Use a probabilistic sampler 

Set `opentracing.jaeger.probabilistic-sampler.sampling-rate` to a value between `0.0` (no sampling) and `1.0` (sampling of every request)

### Propagate headers in B3 format (for compatibility with Zipkin collectors)

Set `opentracing.jaeger.enable-b3-propagation` to `true`

## Advanced cases

### com.uber.jaeger.Tracer.Builder customization

Before creating the `Tracer` it is possible to provide arbitrary customizations to `Tracer.Builder` by providing a bean
of type `JaegerTracerCustomizer`

### Add custom reporter while maintaining the ability to autoconfigure standard ones with properties

By supplying a bean of `ReporterAppender` the user can add custom as many custom `Reporter` as needed without
having the forgo the ability to configure the standard reportes via auto-configuration

### Use OpenTracing's TracerResolver

If the user want's to construct the Tracer using `TracerResolver.resolveTracer` the property `opentracing.jaeger.useTracerResolver`
needs to be set to true (in addition of course to `opentracing.jaeger.enable` being set to true)
In such a case none of this project's defaults are used and neither are the other configuration properties consulted.
Check out the [documentation](https://github.com/jaegertracing/jaeger-client-java/blob/master/jaeger-core/README.md) of TracerResolver
for details on what properties need to be set.
Those properties mentioned in the documentation can be set using any method Spring Boot supports and are not limited
to System Properties and Environment Variables that TraceResolver supports out of the box

## Caution

### Beware of the default sampler in production

In a high traffic environment, the default sampler that is configured is very unsafe since it samples every request.
It is therefore highly recommended to explicitly configure on of the other options in a production environment


## Development

### Executing tests

In order for all tests to run correctly, the docker daemon need to be running on the system

Run the tests be executing

`mvn clean test` 
