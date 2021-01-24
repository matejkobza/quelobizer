package sk.matejkobza.quelobizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import sk.matejkobza.quelobizer.model.ViewOptions;
import sk.matejkobza.quelobizer.service.ViewOptionsManager;

import javax.inject.Inject;

@Controller
public class SettingsController {

    @Inject
    private ViewOptionsManager viewOptionsManager;

    @GetMapping("/settings")
    public ModelAndView settings(ModelAndView modelAndView) {
        modelAndView.setViewName("settings");
        modelAndView.addObject("viewOptions", viewOptionsManager.get());
        return modelAndView;
    }

    @PostMapping("/settings")
    public ModelAndView saveSettings(@ModelAttribute ViewOptions optionsToSave, ModelAndView modelAndView) {
        modelAndView.setViewName("settings");
        this.viewOptionsManager.save(optionsToSave);

        modelAndView.addObject("viewOptions", viewOptionsManager.get());
        modelAndView.addObject("isSaved", true);
        return modelAndView;
    }

}
