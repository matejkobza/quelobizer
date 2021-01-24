package sk.matejkobza.quelobizer.service;

import org.springframework.stereotype.Service;
import sk.matejkobza.quelobizer.model.ViewOptions;

@Service
public class ViewOptionsManager {

    private ViewOptions viewOptions = new ViewOptions();

    public ViewOptions get() {
        return this.viewOptions;
    }

    public void save(ViewOptions viewOptions) {
        this.viewOptions = viewOptions;
    }

}
