package ru.otus.hw;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.StreamsIOService;
import ru.otus.hw.service.TestRunnerService;

public class Application {
    public static void main(String[] args) {
        //Прописать бины в spring-context.xml и создать контекст на его основе
        ApplicationContext context  =
                new ClassPathXmlApplicationContext("/spring-context.xml");
        var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();

    }
}