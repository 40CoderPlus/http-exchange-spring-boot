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

package com.fortycoderplus.http.exchange.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

public class HttpExchangeAutoConfigure {

    @Bean
    @ConditionalOnBean({RestClient.class})
    @ConditionalOnWebApplication(type = Type.SERVLET)
    @ConditionalOnMissingBean
    public HttpServiceProxyFactory proxyFactory(RestClient restClient) {
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();
    }

    @Bean
    @ConditionalOnBean({WebClient.class})
    @ConditionalOnWebApplication(type = Type.REACTIVE)
    @ConditionalOnMissingBean
    public HttpServiceProxyFactory proxyFactory(WebClient webClient) {
        return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient))
                .build();
    }

    @Order(-1)
    @Bean
    @ConditionalOnBean({RestClient.class})
    @ConditionalOnWebApplication(type = Type.SERVLET)
    public HttpExchangeAdapterCreator webmvcHttpExchangeAdapterCreator() {
        return new WebmvcHttpExchangeAdapterCreator();
    }

    @Order(-1)
    @Bean
    @ConditionalOnBean({WebClient.class})
    @ConditionalOnWebApplication(type = Type.REACTIVE)
    public HttpExchangeAdapterCreator webfluxHttpExchangeAdapterCreator() {
        return new WebfluxHttpExchangeAdapterCreator();
    }
}
