package gui.country.combo;

public class Country {
    private String name;
    private String capital;
    private long people;
    private long area;
    public Country(String name, String capital,
                   long people, long area) {
        this.name = name;
        this.capital = capital;
        this.people = people;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public long getPeople() {
        return people;
    }

    public long getArea() {
        return area;
    }

    public int getPopulationDensity() {
        return (int)Math.round((getPeople() / (double)getArea()));
    }


    public String toString() {
        return name;
    }

    public boolean isValid() {
        return people >= 0 && area >= 0;
    }
}