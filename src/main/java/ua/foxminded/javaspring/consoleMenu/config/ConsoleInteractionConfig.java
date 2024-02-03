package ua.foxminded.javaspring.consoleMenu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.consoleMenu.controller.CourseController;
import ua.foxminded.javaspring.consoleMenu.controller.GroupController;
import ua.foxminded.javaspring.consoleMenu.controller.StudentController;
import ua.foxminded.javaspring.consoleMenu.menu.Menu;
import ua.foxminded.javaspring.consoleMenu.menu.MenuInteraction;
import ua.foxminded.javaspring.consoleMenu.service.CourseService;
import ua.foxminded.javaspring.consoleMenu.service.GroupService;
import ua.foxminded.javaspring.consoleMenu.service.StudentService;
import ua.foxminded.javaspring.consoleMenu.util.ApplicationMessages;
import ua.foxminded.javaspring.consoleMenu.util.MyScanner;
import ua.foxminded.javaspring.consoleMenu.util.ReadResourcesFile;
import ua.foxminded.javaspring.consoleMenu.util.console.input.InputHandler;
import ua.foxminded.javaspring.consoleMenu.util.console.output.ConsolePrinter;

@Component
public class ConsoleInteractionConfig {

    @Bean
    public MyScanner scanner() {
        return new MyScanner();
    }

    @Bean
    public MenuInteraction menuInteraction(ConsolePrinter consolePrinter, InputHandler consoleInput, StudentController studentController, GroupController groupController, CourseController courseController) {
        return new MenuInteraction(consoleInput, studentController, courseController, groupController, consolePrinter);
    }

    @Bean
    public ConsolePrinter consolePrinter(CourseService courseService, GroupService groupService, Menu menu, ApplicationMessages messages) {
        return new ConsolePrinter(groupService, courseService, menu, messages);
    }

    @Bean
    public InputHandler input(MyScanner scanner, ConsolePrinter consolePrinter, StudentService studentService, ApplicationMessages messages) {
        return new InputHandler(scanner, consolePrinter, studentService, messages);
    }

    @Bean
    public ReadResourcesFile readFile(ResourceLoader resourceLoader) {
        return new ReadResourcesFile(resourceLoader);
    }

    @Bean
    public Menu menu(ReadResourcesFile readResourcesFile) {
        return new Menu(readResourcesFile);
    }
}
