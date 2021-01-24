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
import sk.matejkobza.quelobizer.model.ViewOptions;
import sk.matejkobza.quelobizer.parser.QueleaParser;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@Controller
public class HomeController {

    @Inject
    private QueleaParser queleaParser;

    @Inject
    private QueleaProperties queleaProperties;

    @Inject
    private ViewOptions viewOptions;

    @Value("${server.port:8080}")
    private long port;

    @GetMapping({"/", "/test", "/lyrics", "/test/lyrics"})
    public ModelAndView home(ModelAndView modelAndView, HttpServletRequest request) throws IOException {
        if (request.getRequestURI().contains("lyrics")) {
            modelAndView.setViewName("index :: lyrics");
        } else {
            modelAndView.setViewName("index");
        }

        if (request.getRequestURI().contains("test")) {
            modelAndView.addObject("lyrics", new Date());
        } else {
            QueleaStatus status = queleaParser.getStatus();
            if (status.isTextVisible()) {
                modelAndView.addObject("lyrics", queleaParser.getLyrics());
            }

            viewOptions.setOverlay(!status.isBlankScreen());
        }

        modelAndView.addObject("isTest", request.getRequestURI().contains("test"));
        modelAndView.addObject("options", viewOptions);
        modelAndView.addObject("refreshInterval", queleaProperties.getRefreshInterval());
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
        if (StringUtils.isEmpty(queleaProperties.getUrl())) {
            throw new IllegalArgumentException(queleaProperties.getUrl());
        }
        this.queleaProperties.setUrl(queleaProperties.getUrl());
        this.queleaProperties.setPassword(queleaProperties.getPassword());
        if (queleaParser.login()) {
            queleaParser.getStatus();
            modelAndView.addObject("defaultUrl", "http://localhost:" + port);
            modelAndView.setViewName("success");
        } else {
            this.queleaProperties.clear();
            modelAndView.addObject("error", "Wrong quelea url");
            modelAndView.setViewName("config");
        }
        return modelAndView;
    }
}
