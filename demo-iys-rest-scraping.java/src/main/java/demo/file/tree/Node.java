package demo.file.tree;

import java.util.Collection;

interface Node {

    int getLevel();

    String getName();

    Collection<Node> getChildren();

    Node addChild(String name);
}
