package demo.model;

import com.google.api.client.util.Key;

public class Target implements Comparable<Target> {

    @Key("id")
    private final String id;

    @Key("name")
    private final String name;

    public Target(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Target that = (Target) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(Target that) {
        return this.getName().compareTo(that.getName());
    }
}
