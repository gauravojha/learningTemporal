package dev.ojha.learningtemporal.workflows;

import dev.ojha.learningtemporal.activities.HelloWorldActivities;
import dev.ojha.learningtemporal.models.Person;
import dev.ojha.learningtemporal.shared.Constants;
import io.temporal.activity.ActivityOptions;
import io.temporal.api.enums.v1.ParentClosePolicy;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.ChildWorkflowCancellationType;
import io.temporal.workflow.ChildWorkflowOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@WorkflowImpl(taskQueues = Constants.HELLO_WORLD_TASK_QUEUE)
public class HelloWorldWorkflowImpl implements HelloWorldWorkflow {

    ActivityOptions options = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(60))
            .build();

    private final HelloWorldActivities activity = Workflow.newActivityStub(HelloWorldActivities.class, options);

    @Override
    public String getGreeting(Person person) {
        log.info("About to trigger child workflow");


        var currentId = Workflow.getInfo().getWorkflowId();
        log.info("This is the currentID: {}", currentId);
        var childWorkflowOptions = ChildWorkflowOptions.newBuilder()

                // if we reuse the workflow ID and set it, it causes undeterministic errors in replay tests
                // to replicate replay errors, pass in the child options like so (uncomment the below line)
                // so that it sets te workflow ID for the child by adding a suffix to parent workflow ID
                // there are 2 payloads in test/resources folder -> history_passing and history_failing
                // the passing is the history when we didnt set the workflow ID
                // the failing is the history when we did

                //.setWorkflowId(currentId + "-child")
                .setWorkflowExecutionTimeout(Duration.ofDays(21))
                .setParentClosePolicy(ParentClosePolicy.PARENT_CLOSE_POLICY_REQUEST_CANCEL)
                .setCancellationType(ChildWorkflowCancellationType.WAIT_CANCELLATION_REQUESTED)
                .build();


        var childWorkflow = Workflow.newChildWorkflowStub(HelloWorldChildWorkflow.class, childWorkflowOptions);
        var child = childWorkflow.getChildGreeting(person);
        return activity.composeGreeting(person) + child;
    }
}
