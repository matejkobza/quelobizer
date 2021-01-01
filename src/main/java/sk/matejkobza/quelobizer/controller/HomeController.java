package sk.matejkobza.quelobizer.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import sk.matejkobza.quelobizer.model.QueleaProperties;
import sk.matejkobza.quelobizer.model.QueleaStatus;
import sk.matejkobza.quelobizer.parser.QueleaParser;

import javax.inject.Inject;
import java.io.IOException;

@Controller
public class HomeController {

    @Inject
    private QueleaParser queleaParser;

    @Inject
    private QueleaProperties queleaProperties;

    @Value("${server.port:8080}")
    private long port;

    @GetMapping("/")
    public ModelAndView home(ModelAndView modelAndView) throws IOException {
        modelAndView.setViewName("index");

        QueleaStatus status = queleaParser.getStatus();

        String lyrics = "";
        if (status.isTextVisible()) {
            lyrics = queleaParser.getLyrics();
        }

        modelAndView.addObject("lyrics", lyrics);
        return modelAndView;
    }

    @GetMapping("/config")
    public ModelAndView config(ModelAndView modelAndView) {
        modelAndView.setViewName("config");
        modelAndView.addObject("queleaProperties", new QueleaProperties());
        return modelAndView;
    }

    @PostMapping("/config")
    public ModelAndView submitConfig(@ModelAttribute QueleaProperties queleaProperties, ModelAndView modelAndView) {
        try {
            if (StringUtils.isEmpty(queleaProperties.getUrl())) {
                throw new IllegalArgumentException(queleaProperties.getUrl());
            }
            this.queleaProperties.setUrl(queleaProperties.getUrl());
            this.queleaProperties.setPassword(queleaProperties.getPassword());
            queleaParser.login();
            queleaParser.getStatus();
            modelAndView.addObject("defaultUrl", "http://localhost:" + port);
            modelAndView.setViewName("success");
        } catch (IOException | IllegalArgumentException e) {
            this.queleaProperties.clear();
            modelAndView.addObject("error", "Wrong quelea url");
            modelAndView.setViewName("config");
        }
        return modelAndView;
    }

}
