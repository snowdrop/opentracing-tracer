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

package me.snowdrop.opentracing.tracer;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("opentracing.jaeger")
public class JaegerConfigurationProperties {

    /**
     * Enable Jaeger Tracer
     */
    private boolean enabled = true;

    /**
     * If this options is enabled, then the instantiation of the Tracer
     * will be handed off to the {@link com.uber.jaeger.tracerresolver.JaegerTracerResolver}
     * Enabling this option means that none of the autoconfigured beans apply.
     * Furthermore the mere setting of this option to true
     * is not enough for it to take effect,
     * {@link com.uber.jaeger.tracerresolver.JaegerTracerResolver} also needs to be on the classpath
     */
    private boolean useTracerResolver = false;

    /**
     * The serviceName that the tracer will use
     */
    private String serviceName = "spring-boot";

    /**
     * Whether spans should be logged to the console
     */
    private boolean logSpans = false;

    private HttpReporter httpReporter = new HttpReporter();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isUseTracerResolver() {
        return useTracerResolver;
    }

    public void setUseTracerResolver(boolean useTracerResolver) {
        this.useTracerResolver = useTracerResolver;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isLogSpans() {
        return logSpans;
    }

    public void setLogSpans(boolean logSpans) {
        this.logSpans = logSpans;
    }

    public HttpReporter getHttpReporter() {
        return httpReporter;
    }

    public void setHttpReporter(HttpReporter httpReporter) {
        this.httpReporter = httpReporter;
    }

    public static class HttpReporter {

        private String url;

        private int flushInterval = 1000;

        private int maxQueueSize = 100;

        private int maxPayload = 1048576;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getFlushInterval() {
            return flushInterval;
        }

        public void setFlushInterval(int flushInterval) {
            this.flushInterval = flushInterval;
        }

        public int getMaxQueueSize() {
            return maxQueueSize;
        }

        public void setMaxQueueSize(int maxQueueSize) {
            this.maxQueueSize = maxQueueSize;
        }

        public int getMaxPayload() {
            return maxPayload;
        }

        public void setMaxPayload(int maxPayload) {
            this.maxPayload = maxPayload;
        }
    }
}
