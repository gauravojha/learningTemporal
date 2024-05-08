package dev.ojha.learningtemporal.activities;

import dev.ojha.learningtemporal.models.Person;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface HelloWorldActivities {
    String composeGreeting(Person person);
    String composeChildGreeting(Person person);
}
