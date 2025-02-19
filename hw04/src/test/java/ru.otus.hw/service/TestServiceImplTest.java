package ru.otus.hw.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.domain.Answer;

import java.util.List;

@SpringBootTest
public class TestServiceImplTest {

    @MockBean
    //@Qualifier("StreamsIOService")
    private IOService ioService;

    @Test
    public void testIsRightAnswer() {
        Answer answer1,answer2,answer3;
        answer1=new Answer("Science doesn't know this yet",true);
        answer2=new Answer("Certainly. The red UFO is from Mars. And green is from Venus",false);
        List<Answer> answers=List.of(answer1,answer2);
        Mockito.when(ioService.readIntForRange(0, answers.size() - 1,
                "Please enter number between 0 and " + answers.size() + ".")).thenReturn(0,1);
        int firstNumAnswer=ioService.readIntForRange(0, answers.size() - 1,
                "Please enter number between 0 and " + answers.size() + ".");
        int secondNumAnswer=ioService.readIntForRange(0, answers.size() - 1,
                "Please enter number between 0 and " + answers.size() + ".");
        Assertions.assertTrue(answers.get(firstNumAnswer).isCorrect());
        Assertions.assertFalse(answers.get(secondNumAnswer).isCorrect());
    }
}
