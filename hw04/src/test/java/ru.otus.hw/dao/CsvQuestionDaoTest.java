package ru.otus.hw.dao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.config.LocaleConfig;
import ru.otus.hw.config.TestConfig;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Question;;
import ru.otus.hw.domain.Answer;

import java.util.List;
import java.util.Locale;

@SpringBootTest
//@EnableConfigurationProperties(AppProperties.class)
public class CsvQuestionDaoTest {

    @MockBean
    private LocaleConfig localeConfig;

    @MockBean
    private TestConfig testConfig;

    @MockBean
    private TestFileNameProvider fileNameProvider;

    @Autowired
    private CsvQuestionDao csvQuestionDao;

    @Test
    void testFindAll() {
        Answer answer1, answer2, answer3, answer4;
        Mockito.when(fileNameProvider.getTestFileName()).thenReturn("questions.csv");
        answer1 = new Answer("Science doesn't know this yet", true);
        answer2 = new Answer("Certainly. The red UFO is from Mars. And green is from Venus", false);
        answer3 = new Answer("Absolutely not", false);
        Question question1 = new Question("Is there life on Mars?", List.of(answer1, answer2, answer3));

        answer1 = new Answer("ClassLoader#geResourceAsStream or ClassPathResource#getInputStream", true);
        answer2 = new Answer("ClassLoader#geResource#getFile + FileReader", false);
        answer3 = new Answer("Wingardium Leviosa", false);
        Question question2 = new Question("How should resources be loaded form jar in Java?", List.of(answer1, answer2, answer3));

        answer1 = new Answer("@SneakyThrow", false);
        answer2 = new Answer("e.printStackTrace()", false);
        answer3 = new Answer("Rethrow with wrapping in business exception (for example, QuestionReadException)", true);
        answer4 = new Answer("Ignoring exception", false);
        Question question3 = new Question("Which option is a good way to handle the exception?", List.of(answer1, answer2, answer3, answer4));

        answer1 = new Answer("System.out.println(\"Hello World\")", true);
        answer2 = new Answer("print (\"Hello World\")", false);
        answer3 = new Answer("echo(\"Hello World\")", false);
        answer4 = new Answer("Console.WriteLine(\"Hello World\")", false);
        Question question4 = new Question("What is a correct syntax to output \"Hello World\" in Java?", List.of(answer1, answer2, answer3, answer4));

        answer1 = new Answer("// This is a comment", true);
        answer2 = new Answer("/* This is a comment", false);
        answer3 = new Answer("# This is a comment", false);
        answer4 = new Answer("-- This is a comment", false);
        Question question5 = new Question("How do you insert COMMENTS in Java code?", List.of(answer1, answer2, answer3, answer4));

        List<Question> questionsTest = List.of(question1, question2, question3, question4, question5);

        List<Question> questions = csvQuestionDao.findAll();

        Assertions.assertIterableEquals(questionsTest, questions);
    }
}
