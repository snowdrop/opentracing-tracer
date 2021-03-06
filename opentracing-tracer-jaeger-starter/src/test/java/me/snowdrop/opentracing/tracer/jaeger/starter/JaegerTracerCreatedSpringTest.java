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

package me.snowdrop.opentracing.tracer.jaeger.starter;

import io.opentracing.Tracer;
import me.snowdrop.opentracing.tracer.JaegerAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = JaegerAutoConfiguration.class,
        properties = {
                "spring.main.banner-mode=off",
                "opentracing.jaeger.enabled=true"
        }
)
public class JaegerTracerCreatedSpringTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testContextLoadsAndContainsTracer() {
        context.getBean(Tracer.class);
    }

}
