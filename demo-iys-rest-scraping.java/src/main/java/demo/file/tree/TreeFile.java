package demo.file.tree;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import demo.file.TextFile;
import demo.json.TargetsListFile;
import demo.model.Target;
import demo.model.TargetsList;

import java.util.List;
import java.util.stream.Collectors;

public class TreeFile {

    private static final ImmutableSet<String> SKIP_PHRASES = ImmutableSet.of(
            "Language Services",
            "Your area of expertise"
    );

    private final String fileName;

    private TreeFile(String fileName) {
        this.fileName = fileName;
    }

    public static void main(String[] args) {
        TreeFile treeFile = new TreeFile("reader/skills.json");
        treeFile.process();
    }

    private void process() {
        TargetsList targetsList = new TargetsListFile(fileName).load();
        List<String> targets = targetsList.getTargets()
                .stream()
                .map(Target::getName)
                .collect(Collectors.toList());

        Node parent = new SimpleNode(0, "");
        for (String target : targets) {
            if (!skip(target)) {
                Node parentLocal = parent;
                List<String> children = Lists.reverse(Lists.newArrayList(Splitter.on("<=").trimResults().split(target)));
                for (String child : children) {
                    parentLocal = parentLocal.addChild(child);
                }
            }
        }

        save(parent, 1);
        save(parent, 2);
        save(parent, 3);
        save(parent, 100);
    }

    private boolean skip(String target) {
        for (String skipPhrase : SKIP_PHRASES) {
            if (target.contains(skipPhrase)) {
                return true;
            }
        }
        return false;
    }

    private void save(Node parent, int levelMax) {
        List<String> values = Lists.newArrayList();
        makeValues(levelMax, parent, values);
        new TextFile(fileName.replace(".json", ".level " + levelMax + ".txt")).save(values);
    }

    private void makeValues(int levelMax, Node node, List<String> values) {
        if (node.getLevel() != 0) {
            values.add(getIndent(node.getLevel()) + node.getName());
        }
        if (node.getLevel() < levelMax) {
            for (Node child : node.getChildren()) {
                makeValues(levelMax, child, values);
            }
        }
    }

    private String getIndent(int level) {
        String indent = "";
        while (level-- > 1) {
            indent = indent + "    ";
        }
        return indent;
    }
}
