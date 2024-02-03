package ua.foxminded.javaspring.consoleMenu.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReadResourcesFileTest {
    @Mock
    private Resource resource;
    @Mock
    private ResourceLoader resourceLoader;

    @InjectMocks
    private ReadResourcesFile resourcesFile;

    private static final String FILE_PATH = "tables/course.txt";
    private static final String SCRIPT = "CREATE TABLE IF NOT EXISTS courses\n" + "(\n"
            + "course_id serial PRIMARY KEY,\n" + "course_name varchar(50) NOT NULL,\n"
            + "course_description varchar(100) NOT NULL\n" + ")\n" + "\n" + "TABLESPACE pg_default;";

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getData_shouldReturnListOfString_whenIsValidDataProvided() throws IOException {
        List<String> expect = Arrays.asList(SCRIPT.split("\n"));
        InputStream inputStream = new ByteArrayInputStream(SCRIPT.getBytes(StandardCharsets.UTF_8));

        when(resourceLoader.getResource(FILE_PATH)).thenReturn(resource);
        when(resource.getInputStream()).thenReturn(inputStream);

        List<String> result = resourcesFile.getData(FILE_PATH);

        assertThat(result).isEqualTo(expect);

        verify(resourceLoader).getResource(FILE_PATH);
    }

    @Test
    void getScript_shouldReturnScriptAsString_whenIsValidDataProvided() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(SCRIPT.getBytes(StandardCharsets.UTF_8));

        when(resourceLoader.getResource(FILE_PATH)).thenReturn(resource);
        when(resource.getInputStream()).thenReturn(inputStream);

        String result = resourcesFile.getScript(FILE_PATH).trim();
        assertEquals(SCRIPT, result);

        verify(resourceLoader).getResource(FILE_PATH);
    }

    @Test
    void getData_shouldReturnRuntimeException_whenResourceIsNotFound() throws IOException {
        when(resourceLoader.getResource(FILE_PATH)).thenReturn(resource);
        when(resource.getInputStream()).thenThrow(new IOException());

        assertThrows(RuntimeException.class, () -> resourcesFile.getData(FILE_PATH));

        verify(resourceLoader).getResource(FILE_PATH);
    }
}