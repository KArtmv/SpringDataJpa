package ua.foxminded.javaspring.consoleMenu.menu;

import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.javaspring.consoleMenu.util.ReadResourcesFile;

public class Menu {

    private static final String menuFilePath = "menu/menu.txt";
    private ReadResourcesFile readFile;

    @Autowired
    public Menu(ReadResourcesFile readFile) {
        this.readFile = readFile;
    }

    public String getOptions() {
        return readFile.getScript(menuFilePath);
    }
}
