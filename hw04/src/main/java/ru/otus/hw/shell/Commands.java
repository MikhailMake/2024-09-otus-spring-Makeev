package ru.otus.hw.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.service.TestRunnerService;

@ShellComponent
public class Commands {

    private final TestRunnerService testRunnerService;

    public Commands(TestRunnerService testRunnerService) {
        this.testRunnerService = testRunnerService;
    }

    @ShellMethod(value = "Start Test", key = "start")
    public void startTest() {
        testRunnerService.run();
    }
}


