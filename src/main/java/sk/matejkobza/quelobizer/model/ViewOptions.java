package sk.matejkobza.quelobizer.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ViewOptions {

    private boolean overlay = true;

}
