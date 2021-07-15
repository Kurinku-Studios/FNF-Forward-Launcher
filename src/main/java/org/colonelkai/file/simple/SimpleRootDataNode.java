package org.colonelkai.file.simple;

import org.colonelkai.file.DataNode;

import java.util.*;

public class SimpleRootDataNode implements DataNode {

    private final Set<DataNode> children = new HashSet<>();

    @Override
    public Collection<DataNode> getChildren() {
        return this.children;
    }

    @Override
    public String[] getNode() {
        return new String[0];
    }

    @Override
    public SimpleDataNode at(String nodePath) {
        SimpleDataNode node = new SimpleDataNode(null, nodePath);
        this.children.add(node);
        return node;
    }

    @Override
    @Deprecated
    public Optional<String> getString() {
        return Optional.empty();
    }

    @Override
    @Deprecated
    public Optional<Integer> getInt() {
        return Optional.empty();
    }

    @Override
    @Deprecated
    public Optional<Double> getDouble() {
        return Optional.empty();
    }

    @Override
    @Deprecated
    public Optional<Boolean> getBoolean() {
        return Optional.empty();
    }

    @Override
    @Deprecated
    public List<DataNode> getCollection() {
        return null;
    }

    @Override
    @Deprecated
    public void set(Object value) {

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.children.forEach(child -> builder.append("\t").append(child.toString()).append("\n"));
        return builder.toString();
    }
}
