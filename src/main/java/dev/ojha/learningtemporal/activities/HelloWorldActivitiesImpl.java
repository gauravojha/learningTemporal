package dev.ojha.learningtemporal.activities;

import dev.ojha.learningtemporal.models.Person;
import dev.ojha.learningtemporal.shared.Constants;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.stereotype.Component;

@ActivityImpl(taskQueues = Constants.HELLO_WORLD_TASK_QUEUE)
@Component
public class HelloWorldActivitiesImpl implements HelloWorldActivities {

    @Override
    public String composeGreeting(Person person) {
        return "Hello " + person.getName() + person.getAge() + " years old!";
    }

    @Override
    public String composeChildGreeting(Person person) {
        return "Hello Child" + person.getName() + person.getAge() + " years old!";
    }
}
