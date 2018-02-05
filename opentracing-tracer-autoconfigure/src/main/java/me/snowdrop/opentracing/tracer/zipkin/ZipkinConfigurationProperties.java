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

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("opentracing.zipkin")
public class ZipkinConfigurationProperties {

    /**
     * Enable Zipkin/Brave Tracer
     */
    private boolean enabled = true;

    /**
     * The serviceName that the tracer will use
     */
    private String serviceName = "spring-boot";

    private HttpSenderProperties httpSenderProperties = new HttpSenderProperties();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public HttpSenderProperties getHttpSenderProperties() {
        return httpSenderProperties;
    }

    public void setHttpSenderProperties(HttpSenderProperties httpSenderProperties) {
        this.httpSenderProperties = httpSenderProperties;
    }

    public static class HttpSenderProperties {

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}