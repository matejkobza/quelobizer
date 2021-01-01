package sk.matejkobza.quelobizer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QueleaStatus {

    private boolean isLogo;
    private boolean isBlackScreen;
    private boolean isClearScreen;

    public boolean isTextVisible() {
        return !isLogo && !isBlackScreen && !isClearScreen;
    }

}
