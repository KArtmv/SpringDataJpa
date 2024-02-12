package ua.foxminded.javaspring.consoleMenu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReadResourcesFile {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadResourcesFile.class);

    private ResourceLoader resourceLoader;

    @Autowired
    public ReadResourcesFile(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String getScript(String filePath) {
        StringBuilder currentStatement = new StringBuilder();
        List<String> lines = getData(filePath);
        lines.forEach(line -> currentStatement.append(line.trim()).append("\n"));
        return currentStatement.toString();
    }

    public List<String> getData(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getDataResource(filePath)))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.debug("Failed to read file: {}.", e.getMessage());
        }
        return lines;
    }

    private InputStream getDataResource(String filePath) {
        InputStream inputStream = null;
        try {
            inputStream = resourceLoader.getResource(filePath).getInputStream();
        } catch (IOException e) {
            LOGGER.debug("Failed to get resource: {}.", e.getMessage());
        }
        return inputStream;
    }
}
