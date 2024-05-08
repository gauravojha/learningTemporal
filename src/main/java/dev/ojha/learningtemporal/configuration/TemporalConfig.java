package dev.ojha.learningtemporal.configuration;

import io.temporal.client.WorkflowClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemporalConfig {

    @Autowired
    private WorkflowClient workflowClient;

}
