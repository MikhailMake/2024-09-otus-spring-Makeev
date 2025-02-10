package ru.otus.hw.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.service.TestRunnerService;

@ShellComponent
public class Commands {

    private ApplicationContext applicationContext;

    @Autowired
    public Commands(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @ShellMethod(value = "Start Test", key = "start")
    public void startTest() {
        var testRunnerService = applicationContext.getBean(TestRunnerService.class);
        testRunnerService.run();
    }
}


