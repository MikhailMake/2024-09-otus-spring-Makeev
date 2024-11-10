package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final IOService ioService;

    private final QuestionDao qestionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        // Получить вопросы из дао и вывести их с вариантами ответов
        List<Question> questions = getData();
        for (Question question : questions) {
            printQuestion(question);
        }
    }

    private List<Question> getData() {
        return qestionDao.findAll();
    }

    private void printQuestion(Question question) {
        ioService.printLine(question.text());
        List<Answer> answers = question.answers();
        for (int k = 0; k < answers.size(); k++) {
            ioService.printLine(k  + ". " + answers.get(k).text());
        }
    }
}
