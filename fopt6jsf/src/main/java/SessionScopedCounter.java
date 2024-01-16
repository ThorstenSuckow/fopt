import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class SessionScopedCounter extends Counter implements Serializable {
}
