package dev.ojha.learningtemporal.configuration;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.opentracingshim.OpenTracingShim;
import io.opentracing.util.GlobalTracer;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.opentracing.OpenTracingClientInterceptor;
import io.temporal.opentracing.OpenTracingOptions;
import io.temporal.opentracing.OpenTracingSpanContextCodec;
import io.temporal.opentracing.OpenTracingWorkerInterceptor;
import io.temporal.spring.boot.TemporalOptionsCustomizer;
import io.temporal.worker.WorkerFactoryOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemporalConfig {

    @Autowired
    private WorkflowClient workflowClient;


    @Bean
    public TemporalOptionsCustomizer<WorkflowClientOptions.Builder>
    customWorkflowClientOptionsBuilder(OpenTelemetry openTelemetry) {
        io.opentracing.Tracer tracer = OpenTracingShim.createTracerShim(openTelemetry);
        GlobalTracer.registerIfAbsent(tracer);
        OpenTracingOptions options =
                OpenTracingOptions.newBuilder()
                        .setTracer(tracer)
                        .setSpanContextCodec(OpenTracingSpanContextCodec.TEXT_MAP_CODEC)
                        .build();
        return (optionsBuilder) -> {
            optionsBuilder.setInterceptors(new OpenTracingClientInterceptor(options));
            return optionsBuilder;
        };
    }

    @Bean
    public TemporalOptionsCustomizer<WorkerFactoryOptions.Builder> customWorkerFactoryOptions(
            OpenTelemetry openTelemetry) {
        io.opentracing.Tracer tracer = OpenTracingShim.createTracerShim(openTelemetry);
        GlobalTracer.registerIfAbsent(tracer);
        OpenTracingOptions options =
                OpenTracingOptions.newBuilder()
                        .setTracer(tracer)
                        .setSpanContextCodec(OpenTracingSpanContextCodec.TEXT_MAP_CODEC)
                        .build();
        return (optionsBuilder) -> {
            optionsBuilder.setWorkerInterceptors(new OpenTracingWorkerInterceptor(options));
            return optionsBuilder;
        };
    }
}
