import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Greeter {

    private String name;

    public Greeter() {
        name = "Hello World!";
    }


    public String getName() {
        return name;
    }

}
