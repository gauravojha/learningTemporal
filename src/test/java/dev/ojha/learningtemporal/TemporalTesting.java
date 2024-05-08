package dev.ojha.learningtemporal;

import dev.ojha.learningtemporal.workflows.HelloWorldWorkflowImpl;
import io.temporal.testing.WorkflowReplayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemporalTesting {
    private ClassLoader classLoader;

    @BeforeEach
    public void setup() {
        classLoader = getClass().getClassLoader();
    }

    @Test
    public void testUnSuccessfulReplayFromFile() throws Exception {

        File eventHistoryFile =
                new File(
                        Objects.requireNonNull(
                                        classLoader.getResource(
                                                "history_failing.json"))
                                .getFile());
        assertDoesNotThrow(() -> WorkflowReplayer.replayWorkflowExecution(eventHistoryFile,
                HelloWorldWorkflowImpl.class));

    }

    @Test
    public void testSuccessfulReplayFromFile() throws Exception {

        File eventHistoryFile =
                new File(
                        Objects.requireNonNull(
                                        classLoader.getResource(
                                                "history_passing.json"))
                                .getFile());
        assertDoesNotThrow(() -> WorkflowReplayer.replayWorkflowExecution(eventHistoryFile,
                HelloWorldWorkflowImpl.class));

    }

}
