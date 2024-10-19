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
        List<Question> beans = qestionDao.findAll();
        try {
            for (int i = 0; i < beans.size(); i++) {
                ioService.printLine(beans.get(i).text());
                List<Answer> answers = beans.get(i).answers();
                for (int k = 0; k < answers.size(); k++) {
                    ioService.printLine(k + 1 + ". " + answers.get(k).text() +
                            " [" + String.valueOf(answers.get(k).isCorrect()) + "]");
                }
            }
        } catch (Exception e) {
            ioService.printLine(e.getMessage());
        }
    }
}
