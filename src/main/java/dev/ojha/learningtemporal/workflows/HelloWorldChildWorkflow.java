package dev.ojha.learningtemporal.workflows;

import dev.ojha.learningtemporal.models.Person;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface HelloWorldChildWorkflow {

    @WorkflowMethod
    String getChildGreeting(Person person);
}
