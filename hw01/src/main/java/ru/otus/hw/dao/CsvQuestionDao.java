package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() throws QuestionReadException {
        List<QuestionDto> beans = new ArrayList<>();
        List<Question> listQuestion = new ArrayList<>();
        try {
            beans = new CsvToBeanBuilder(new FileReader(fileNameProvider.getTestFileName()))
                    .withType(QuestionDto.class).withSeparator(';').withSkipLines(1).build().parse();
            listQuestion = beans.stream().map(QuestionDto::toDomainObject).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            throw new QuestionReadException(e.getMessage());
        }
        return listQuestion;
    }
}
