import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class ViewScopedCounter extends Counter implements Serializable {
}
