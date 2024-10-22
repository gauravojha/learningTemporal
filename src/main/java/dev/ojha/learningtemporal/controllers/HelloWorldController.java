package dev.ojha.learningtemporal.controllers;

import dev.ojha.learningtemporal.models.Person;
import dev.ojha.learningtemporal.shared.Constants;
import dev.ojha.learningtemporal.workflows.HelloWorldWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    @Autowired
    WorkflowClient workflowClient;

    @GetMapping
    public String helloWorld() {
        var workflow = workflowClient
                .newWorkflowStub(HelloWorldWorkflow.class, WorkflowOptions.newBuilder()
                        .setTaskQueue(Constants.HELLO_WORLD_TASK_QUEUE)
                        .setWorkflowId("HelloWorldWorkflow")
                        .build());
    return workflow.getGreeting(new Person("Ojha", 32,"asd"));
    }
}
