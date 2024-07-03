package dev.ojha.learningtemporal.workflows;

import dev.ojha.learningtemporal.activities.HelloWorldActivities;
import dev.ojha.learningtemporal.models.Person;
import dev.ojha.learningtemporal.shared.Constants;
import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@WorkflowImpl(taskQueues = Constants.HELLO_WORLD_TASK_QUEUE)
public class HelloWorldChildWorkflowImpl implements HelloWorldChildWorkflow {

    ActivityOptions options = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(60))
            .build();

    private final HelloWorldActivities activity = Workflow.newActivityStub(HelloWorldActivities.class, options);

    @Override
    public String getChildGreeting(Person person) {
        log.info("Here is the greeting");
    return activity.composeChildGreeting(person);
    }
}
