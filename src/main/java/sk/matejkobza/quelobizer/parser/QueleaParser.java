package sk.matejkobza.quelobizer.parser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import sk.matejkobza.quelobizer.model.QueleaProperties;
import sk.matejkobza.quelobizer.model.QueleaStatus;

import javax.inject.Inject;
import java.io.IOException;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class QueleaParser {

    @Inject
    private QueleaProperties queleaProperties;

    public boolean login() {
        Map<String, String> formData = new HashMap<>();
        formData.put("password", queleaProperties.getPassword());
        try {
            Jsoup.connect(queleaProperties.getUrl())
                    .data(formData)
                    .method(Connection.Method.POST)
//                .userAgent(USER_AGENT)
                    .execute();
            return true;
        } catch (IOException e) {
            log.warn("Unable to login. Check quelea is running and server is enabled in settings");
        }
        return false;
    }

    public QueleaStatus getStatus() {
        if (queleaProperties.getUrl() == null) {
            return new QueleaStatus();
        }
        Optional<Document> status = null;
        try {
            status = fetch(queleaProperties.getUrl() + "/status");
        } catch (IOException e) {
            log.error("Unable to acquire quelea status. Check quelea is running and server is enabled in settings");
            return new QueleaStatus();
        }
        if (status.isPresent()) {
            String body = status.get().body().text();
            if (body.length() == 0) {
                login();
            } else {
                String[] statuses = body.split(",");
                return new QueleaStatus("true".equals(statuses[0]), "true".equals(statuses[1]), "true".equals(statuses[2]));
            }
        }
        return new QueleaStatus();
    }

    public String getLyrics() throws IOException {
        if (queleaProperties.getUrl() == null) {
            return "";
        }
        Optional<Document> lyrics = fetch(queleaProperties.getUrl() + "/lyrics");
        if (lyrics.isPresent()) {
            Elements current = lyrics.get().getElementsByClass("current");
            current.html();
            return current.html();
        }
        return null;
    }

    public Optional<Document> fetch(String url) throws IOException {
        try {
            return Optional.of(Jsoup.connect(url).get());
        } catch (ConnectException e) {
            log.error("Unable to call quelea. Make sure it is running and server is enabled in settings.");
        }
        return Optional.empty();
    }

}
