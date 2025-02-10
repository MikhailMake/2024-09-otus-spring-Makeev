package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
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

    private boolean isRightAnswer(List<Answer> answers) {
        int numAnswer = ioService.readIntForRange(0, answers.size() - 1,
                ioService.getMessage("TestService.is.right.answer",
                        answers.size() - 1));
        return (answers.get(numAnswer).isCorrect());
    }
}
