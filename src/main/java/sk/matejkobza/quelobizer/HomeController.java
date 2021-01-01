package sk.matejkobza.quelobizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class HomeController {

    @Value("${quelea.url}")
    private String url;

    @GetMapping("/")
    public ModelAndView home(ModelAndView modelAndView) throws IOException {
        modelAndView.setViewName("index");
        Document status = Jsoup.connect(url + "/status").get();
        status.body().text();

        Document lyrics = Jsoup.connect(url + "/lyrics").get();
        Elements current = lyrics.getElementsByClass("current");
        current.html();
        modelAndView.addObject("text", current.html());
        return modelAndView;
    }

}
