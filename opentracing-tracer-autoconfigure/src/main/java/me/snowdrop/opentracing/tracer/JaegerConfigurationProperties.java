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

import java.util.concurrent.TimeUnit;

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

    private RemoteReporter remoteRemoteReporter = new RemoteReporter();

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

    public RemoteReporter getRemoteRemoteReporter() {
        return remoteRemoteReporter;
    }

    public void setRemoteRemoteReporter(RemoteReporter remoteRemoteReporter) {
        this.remoteRemoteReporter = remoteRemoteReporter;
    }

    private static class RemoteReporter {

        private int flushInterval = 10;

        private TimeUnit flushTimeUnit = TimeUnit.MILLISECONDS;


        public int getFlushInterval() {
            return flushInterval;
        }

        public void setFlushInterval(int flushInterval) {
            this.flushInterval = flushInterval;
        }

        public TimeUnit getFlushTimeUnit() {
            return flushTimeUnit;
        }

        public void setFlushTimeUnit(TimeUnit flushTimeUnit) {
            this.flushTimeUnit = flushTimeUnit;
        }
    }
}
