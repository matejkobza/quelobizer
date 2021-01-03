package sk.matejkobza.quelobizer.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class QueleaProperties {

    private String url;
    private String password;

    @Value("${quelea.refreshInterval}")
    private Long refreshInterval;

    public void clear() {
        this.url = null;
        this.password = null;
    }
}
