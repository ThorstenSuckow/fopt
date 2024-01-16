import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;


@Named("choices")
@ApplicationScoped
/**
 * @see [Oec22]
 */
public class Options
{
    private List<String> countryOptions;
    private List<String> languageOptions;

    public Options()
    {
        countryOptions = new ArrayList<>();
        countryOptions.add("Dänemark");
        countryOptions.add("Deutschland");
        countryOptions.add("Frankreich");
        countryOptions.add("Gro�britannien");
        countryOptions.add("Italien");
        countryOptions.add("�sterreich");
        countryOptions.add("Portugal");
        countryOptions.add("Schweiz");
        countryOptions.add("Spanien");

        languageOptions = new ArrayList<>();
        languageOptions.add("C++");
        languageOptions.add("C#");
        languageOptions.add("Delphi");
        languageOptions.add("Java");
        languageOptions.add("JavaScript");
        languageOptions.add("Python");
    }

    public List<String> getCountryOptions()
    {
        return countryOptions;
    }

    public List<String> getLanguageOptions()
    {
        return languageOptions;
    }
}
