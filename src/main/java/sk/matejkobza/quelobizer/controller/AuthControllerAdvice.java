package sk.matejkobza.quelobizer.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import sk.matejkobza.quelobizer.model.QueleaProperties;

import javax.inject.Inject;

@ControllerAdvice
public class AuthControllerAdvice {

    @Inject
    private QueleaProperties properties;

    @ModelAttribute("authenticated")
    public boolean isAuthenticated() {
        return properties.isAuthenticated();
    }

}
