package ru.otus.hw.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StreamsIOServiceTest {


    private static final String TEXT_TEST = "ha-ha";
    private PrintStream backup;
    private ByteArrayOutputStream bos;
    private IOService ioService;

    @BeforeEach
    public void setUp() {
        backup = System.out;
        bos = new ByteArrayOutputStream();
        //System.setOut(new PrintStream(bos));
        ioService = new StreamsIOService(new PrintStream(bos));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(backup);
    }

    @Test
    @DisplayName("Тестирование вывода данных на консоль")
    public void printlnTest() {
        ioService.printLine(TEXT_TEST);
        assertThat(bos.toString()).isEqualTo(TEXT_TEST + System.lineSeparator());
    }
}
