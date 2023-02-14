/*
 * (c) Copyright 2023 40CoderPlus. All rights reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fortycoderplus.http.exchange.sample;

import com.fortycoderplus.http.exchange.autoconfigure.EnableHttpInterfaces;
import com.fortycoderplus.http.exchange.autoconfigure.HttpInterface;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class HttpExchangeSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(HttpExchangeSampleApplication.class, args);
    }

    @HttpInterface(baseUrl = "http://127.0.0.1:8080")
    public interface GreetingService {

        @GetExchange("/greeting")
        Mono<String> greeting(@RequestParam String name);
    }

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

    @Configuration
    @EnableHttpInterfaces(basePackages = "com.fortycoderplus.http.exchange.sample")
    public static class HttpExchangeConfiguration {}
}
