package ua.foxminded.javaspring.consoleMenu.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.consoleMenu.util.ResourceFileReader;

@Component
public class MenuOptionsProvider {

    private static final String menuFilePath = "menu/menu.txt";
    private ResourceFileReader readFile;

    @Autowired
    public MenuOptionsProvider(ResourceFileReader readFile) {
        this.readFile = readFile;
    }

    public String getOptions() {
        return readFile.getScript(menuFilePath);
    }
}
