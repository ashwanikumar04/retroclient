package in.ashwanik.retroclientsample.models;

/**
 * Created by AshwaniK on 1/30/2016.
 */
public class Book {
    public int id;
    public String name;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
