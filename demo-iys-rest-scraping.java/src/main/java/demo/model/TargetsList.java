package demo.model;

import com.google.api.client.util.Key;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TargetsList {

    @Key("size")
    private int size;

    @Key("targets")
    private List<Target> targets;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Target> getTargets() {
        return targets;
    }

    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }

    public void updateTargets(Collection<Target> targets) {
        setTargets(Lists.newArrayList(targets));
        Collections.sort(getTargets());
        setSize(getTargets().size());
    }
}
