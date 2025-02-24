package ru.otus.hw.service;

import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.QuestionReadException;

@Service
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    private final LocalizedIOService ioService;

    public TestRunnerServiceImpl(TestService testService,StudentService studentService,
                                 ResultService resultService,
                                 LocalizedIOService ioService) {
        this.testService = testService;
        this.studentService = studentService;
        this.resultService = resultService;
        this.ioService = ioService;
    }

    @Override
    public void run() {
        try {
            var student = studentService.determineCurrentStudent();
            var testResult = testService.executeTestFor(student);
            resultService.showResult(testResult);
        } catch (QuestionReadException e) {
            ioService.printLineLocalized("TestRunnerService.question.read.exception");
        }
    }
}
