package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() throws QuestionReadException {
        List<QuestionDto> beans;
        List<Question> listQuestion;
        try {
            String fileName = fileNameProvider.getTestFileName();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            if (inputStream == null) {
                throw new QuestionReadException("File " + fileName + " not found");
            } else {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                beans = new CsvToBeanBuilder(inputStreamReader)
                        .withType(QuestionDto.class).withSeparator(';').withSkipLines(1).build().parse();
                listQuestion = beans.stream().map(QuestionDto::toDomainObject).collect(Collectors.toList());
            }
        }  catch (UnsupportedEncodingException e) {
            throw new QuestionReadException(e.getMessage());
        }
        return listQuestion;
    }
}
