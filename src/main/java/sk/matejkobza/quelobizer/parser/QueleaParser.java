package sk.matejkobza.quelobizer.parser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import sk.matejkobza.quelobizer.model.QueleaProperties;
import sk.matejkobza.quelobizer.model.QueleaStatus;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class QueleaParser {

    @Inject
    private QueleaProperties queleaProperties;

    public void login() throws IOException {
        Map<String, String> formData = new HashMap<>();
        formData.put("password", queleaProperties.getPassword());
        Jsoup.connect(queleaProperties.getUrl())
                .data(formData)
                .method(Connection.Method.POST)
//                .userAgent(USER_AGENT)
                .execute();
    }

    public QueleaStatus getStatus() throws IOException {
        Document status = Jsoup.connect(queleaProperties.getUrl() + "/status").get();
        String[] statuses = status.body().text().split(",");
        return new QueleaStatus("true".equals(statuses[0]), "true".equals(statuses[1]), "true".equals(statuses[2]));
    }

    public String getLyrics() throws IOException {
        Document lyrics = Jsoup.connect(queleaProperties.getUrl() + "/lyrics").get();
        Elements current = lyrics.getElementsByClass("current");
        current.html();
        return current.html();
    }

}
