package demo.model;

import java.util.List;

public class SourcesList {

    private final List<Source> sources;
    private final int total;
    private final String code;

    public SourcesList(List<Source> sources, int total, String code) {
        this.sources = sources;
        this.total = total;
        this.code = code;
    }

    public List<Source> getSources() {
        return sources;
    }

    public int getTotal() {
        return total;
    }

    public String getCode() {
        return code;
    }
}
