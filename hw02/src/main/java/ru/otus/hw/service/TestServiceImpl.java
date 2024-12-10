package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);
        for (var question : questions) {
            var isAnswerValid = false;
            printQuestion(question);
            isAnswerValid = isRightAnswer(question.answers());
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private void printQuestion(Question question) {
        ioService.printLine(question.text());
        List<Answer> answers = question.answers();
        for (int k = 0; k < answers.size(); k++) {
            ioService.printLine(k + ". " + answers.get(k).text());
        }
    }

    private Boolean isRightAnswer(List<Answer> answers) {
        Boolean isAnswerValid = false;
        Integer numAnswer = ioService.readIntForRange(0, answers.size() - 1,
                "Please enter number between 0 and " + answers.size() + ".");
        if (answers.get(numAnswer).isCorrect()) {
            isAnswerValid = true;
        }
        return isAnswerValid;
    }
}
