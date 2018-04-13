package demo.file.tree;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;

class SimpleNode implements Node {

    private final int level;
    private final String name;
    private final Map<String, Node> children;

    SimpleNode(int level, String name) {
        this.name = name;
        this.level = level;
        this.children = Maps.newTreeMap();
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<Node> getChildren() {
        return children.values();
    }

    @Override
    public Node addChild(String name) {
        children.computeIfAbsent(name, key -> new SimpleNode(level + 1, name));
        return children.get(name);
    }
}
