package ua.foxminded.javaspring.consoleMenu;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.consoleMenu.menu.MenuInteraction;

@Component
@Profile("!disableAutoStart")
@RequiredArgsConstructor
public class MenuLauncher implements ApplicationRunner {

    private final MenuInteraction menuInteraction;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        menuInteraction.startMenu();
    }
}
