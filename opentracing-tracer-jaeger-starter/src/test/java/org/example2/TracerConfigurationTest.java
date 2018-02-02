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

package org.example2;

import io.opentracing.Tracer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleJaegerTracerApplication.class, properties = {"spring.main.banner-mode=off"})
public class TracerConfigurationTest {

    @Autowired
    private ConfigurableApplicationContext applicationContext;


    @Test
    public void testIfTracerIsConfiguredFromAutoconfigureNotOTContrib() {
        Tracer tracer = applicationContext.getBean(Tracer.class);
        assertThat(tracer).isInstanceOf(com.uber.jaeger.Tracer.class);

        final ConditionEvaluationReport conditionEvaluationReport =
                ConditionEvaluationReport.get(this.applicationContext.getBeanFactory());

        //TODO improve handling of class names

        //assert that the TracerAutoConfiguration was not used
        assertThat(
                conditionEvaluationReport
                        .getConditionAndOutcomesBySource()
                        .get("io.opentracing.contrib.spring.web.autoconfig.TracerAutoConfiguration#getTracer")
                        .isFullMatch()
        ).isFalse();

        //assert that the JaegerAutoConfiguration was not used
        assertThat(conditionEvaluationReport
                .getConditionAndOutcomesBySource()
                .get("me.snowdrop.opentracing.tracer.JaegerAutoConfiguration$ExplicitConfiguration")
                .isFullMatch()
        ).isTrue();
    }


}
