# Spring HTTP Interface Auto Create Proxy Service

Spring [HTTP Interface](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#rest-http-interface) is an HTTP service as a Java interface with annotated methods for HTTP exchanges.

Add annotation `@HttpInterface` for `@HttpExchange(s)`.

Add annotation `@EnableHttpInterfaces` for init beans with annotation `@HttpInterface`.

# Requirements

- Spring Framework 6/Spring Boot 3
- Java 17+

# How to use

Annotation interface by `@HttpInterface`

```java
@HttpInterface(baseUrl = "http://127.0.0.1:8080")
public interface GreetingService {

    @GetExchange("/greeting")
    Mono<String> greeting(@RequestParam String name);
}
```

Use `GreetingService` to communicate with HTTP Server

```java
@AllArgsConstructor
@RestController
public static class GreetingEndpoint {

    private GreetingService greetingService;

    @RequestMapping("/hello")
    public Mono<String> greeting(@RequestParam String name) {
        return greetingService.greeting(name);
    }

    @RequestMapping("/greeting")
    public Mono<String> hello(@RequestParam String name) {
        return Mono.just("Hello " + name);
    }
}
```

More about `@HttpInterface`

Use you own `HttpServiceProxyFactory`
```java
@HttpInterface(proxyFactory = "myProxyFactory")
public interface GreetingService {

    @GetExchange("/greeting")
    Mono<String> greeting(@RequestParam String name);
}
```

Use you own `WebClient`
```java
@HttpInterface(httpExchangeAdapter = "myHttpExchangeAdapter")
public interface GreetingService {

    @GetExchange("/greeting")
    Mono<String> greeting(@RequestParam String name);
}
```

# Learn More

- [HTTP Interface in Spring 6](https://www.baeldung.com/spring-6-http-interface)
- [Spring Declarative HTTP Client using @HttpExchange](https://howtodoinjava.com/spring-webflux/http-declarative-http-client-httpexchange/#5-http-service-interface-example)
