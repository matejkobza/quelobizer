package sk.matejkobza.quelobizer.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.awt.*;

@Getter
@Setter
public class ViewOptions {

    private boolean overlay = true;
    private String textColor = "#000000";
    private String backgroundColor = "#ffffff";
    private String backgroundOpacity = "0.6";
    private String fontFamily = "Arial";

    public String getBackgroundColorRGBA() {
        Color color = Color.decode(backgroundColor);
        return color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "," + backgroundOpacity;
    }

}
