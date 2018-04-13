package demo.file;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class TextFile {

    private final String fileName;

    public TextFile(String fileName) {
        this.fileName = "src/main/resources/" + fileName;
    }

    public String load() {
        try {
            File file = new File(fileName);
            CharSource source = Files.asCharSource(file, Charsets.UTF_8);
            return source.read();
        } catch (IOException e) {
            throw new RuntimeException("Exception during loading the file: " + fileName, e);
        }
    }

    public Iterable<String> loadAsIterable() {
        return Splitter.on('\n')
                .trimResults()
                .omitEmptyStrings()
                .split(load());
    }

    public List<String> loadAsList() {
        return Lists.newArrayList(loadAsIterable());
    }

    public void save(String value) {
        try {
            File file = new File(fileName);
            CharSink sink = Files.asCharSink(file, Charsets.UTF_8);
            sink.write(value);
        } catch (IOException e) {
            throw new RuntimeException("Exception during saving the file: " + fileName, e);
        }
    }

    public void save(List<String> values) {
        save(Joiner.on('\n').join(values));
    }
}