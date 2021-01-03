package sk.matejkobza.quelobizer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QueleaStatus {

    private boolean isLogo = true;
    private boolean isBlankScreen = true;
    private boolean isTextHidden = true;

    public boolean isTextVisible() {
        return !isLogo && !isBlankScreen && !isTextHidden;
    }

}
