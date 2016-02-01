package in.ashwanik.retroclientsample.models;

/**
 * Created by AshwaniK on 1/30/2016.
 */
public class Author {
    public int id;
    public String name;

    public Author() {

    }

    public Author(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
