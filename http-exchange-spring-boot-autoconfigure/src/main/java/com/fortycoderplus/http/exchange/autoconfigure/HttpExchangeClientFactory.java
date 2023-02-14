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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

public class HttpExchangeClientFactory implements FactoryBean<Object>, ApplicationContextAware {

    private Class<?> type;

    private String baseUrl;
    private String webClientName;
    private String proxyFactoryName;

    private ApplicationContext applicationContext;

    @Override
    public Object getObject() {
        if (StringUtils.hasText(baseUrl)) {
            WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();
            HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builder(
                            WebClientAdapter.forClient(webClient))
                    .build();
            return httpServiceProxyFactory.createClient(type);
        }
        try {
            HttpServiceProxyFactory proxyFactory = StringUtils.hasText(proxyFactoryName)
                    ? applicationContext.getBean(HttpServiceProxyFactory.class, proxyFactoryName)
                    : applicationContext.getBean(HttpServiceProxyFactory.class);
            return proxyFactory.createClient(type);
        } catch (BeansException e) {
            WebClient webClient = StringUtils.hasText(webClientName)
                    ? applicationContext.getBean(WebClient.class, webClientName)
                    : applicationContext.getBean(WebClient.class);
            return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
                    .build()
                    .createClient(type);
        }
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    // setters
    public HttpExchangeClientFactory setType(Class<?> type) {
        this.type = type;
        return this;
    }

    public HttpExchangeClientFactory setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public HttpExchangeClientFactory setWebClient(String webClientName) {
        this.webClientName = webClientName;
        return this;
    }

    public HttpExchangeClientFactory setProxyFactory(String proxyFactoryName) {
        this.proxyFactoryName = proxyFactoryName;
        return this;
    }
}
