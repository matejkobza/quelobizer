package sk.matejkobza.quelobizer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
@Slf4j
public class QuelobizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuelobizerApplication.class, args);
    }

    @Inject
    private Environment environment;

    @EventListener(ApplicationReadyEvent.class)
    public void started() {
        String port = environment.getProperty("server.port");
        String configUrl = "http://localhost:" + port + "/config";
        System.setProperty("java.awt.headless", "false");
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI(configUrl));
        } catch (IOException | URISyntaxException e) {
            log.error("Unable to open browser", e);
        }
    }
}
