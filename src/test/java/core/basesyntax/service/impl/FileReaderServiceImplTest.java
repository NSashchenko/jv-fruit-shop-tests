package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {

    private static ReaderServiceImpl fileReaderService;
    private static List<String> dbOutput;
    private static final String CORRECT_PATH = "src/main/java/core/basesyntax"
            + "/resources/database.csv";
    private static final String EMPTY_FILE = "src/main/java/core/basesyntax"
            + "/resources/empty.csv";
    private static final String INCORRECT_PATH = "src/main/java/core/basesyntax"
            + "/resources/No_name.csv";
    private static final String INVALID_FILE_TYPE = "src/main/java/core/basesyntax"
            + "/resources/invalid.txt";

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new ReaderServiceImpl();
        dbOutput = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
    }

    @Test
    void readFile_CorrectPath_ok() {
        List<String> containText = fileReaderService.readFile(CORRECT_PATH);
        containText.remove(0);
        Assertions.assertEquals(dbOutput, containText);
    }

    @Test
    void readFile_IncorrectPath_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFile(INCORRECT_PATH));
    }

    @Test
    void readFile_invalidType_notOk() {
        File invalidFile = new File(INVALID_FILE_TYPE);
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFile(String.valueOf(invalidFile)));
    }

    @Test
    void readFile_EmptyFile_ok() {
        File empty = new File(EMPTY_FILE);
        List<String> expected = Collections.emptyList();
        List<String> actual = fileReaderService.readFile(String.valueOf(empty));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readFile_noInvalidLetters_ok() {
        File inputFile = new File(CORRECT_PATH);
        List<String> containText = fileReaderService.readFile(CORRECT_PATH);
        containText.remove(0);
        for (String line : containText) {
            String[] values = line.split(",");
            String type = values[0].trim();
            assertTrue(type.equals("b")
                            || type.equals("s")
                            || type.equals("p")
                            || type.equals("r"),
                    "Invalid type found in line: " + line);
        }
    }

    @Test
    public void readFile_noExistenceFile_notOk() {
        assertThrows(RuntimeException.class, ()
                -> fileReaderService.readFile(INCORRECT_PATH));
    }
}
