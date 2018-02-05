/*
 *  Copyright 2018 Red Hat, Inc, and individual contributors.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package me.snowdrop.opentracing.tracer.zipkin;

import brave.Tracing;
import brave.opentracing.BraveTracer;
import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

import java.util.Collections;
import java.util.List;

@Configuration
@ConditionalOnClass(brave.opentracing.BraveTracer.class)
@ConditionalOnMissingBean(io.opentracing.Tracer.class)
@ConditionalOnProperty(value = "opentracing.zipkin.enabled", havingValue = "true", matchIfMissing = true)
@AutoConfigureAfter(name = "me.snowdrop.opentracing.tracer.jaeger.JaegerAutoConfiguration")
@AutoConfigureBefore(name = "io.opentracing.contrib.spring.web.autoconfig.TracerAutoConfiguration")
@EnableConfigurationProperties(ZipkinConfigurationProperties.class)
public class ZipkinAutoConfiguration {

    @Autowired(required = false)
    private List<ZipkinTracerCustomizer> tracerCustomizers = Collections.emptyList();

    @Bean
    @ConditionalOnMissingBean
    public io.opentracing.Tracer tracer(ZipkinConfigurationProperties properties,
                          Reporter<Span> reporter,
                          Sampler sampler) {

        final Tracing.Builder builder = Tracing.newBuilder()
                .sampler(sampler)
                .localServiceName(properties.getServiceName())
                .spanReporter(reporter);

        tracerCustomizers.forEach(c -> c.customize(builder));

        return BraveTracer.create(builder.build());
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(OkHttpSender.class)
    @ConditionalOnExpression("'${opentracing.zipkin.httpSenderProperties.url:null}' != 'null'")
    public Sender sender(ZipkinConfigurationProperties properties) {
        return OkHttpSender.create(properties.getHttpSenderProperties().getUrl());
    }


    @Bean
    @ConditionalOnMissingBean
    public Reporter<Span> reporter(@Autowired(required = false) Sender sender) {
        if (sender != null) {
            return AsyncReporter.create(sender);
        }

        return Reporter.NOOP;
    }


    @Bean
    @ConditionalOnMissingBean
    public Sampler sampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}
