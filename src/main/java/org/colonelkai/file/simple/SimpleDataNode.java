package org.colonelkai.file.simple;

import org.colonelkai.file.DataNode;

import java.util.*;

public class SimpleDataNode implements DataNode {

    private final String[] at;
    private final Set<DataNode> children = new HashSet<>();
    private Object value;

    public SimpleDataNode(Object value, String... at) {
        if (at == null || at.length == 0) {
            throw new IllegalArgumentException("SimpleDataNode requires a location, use SimpleRootDataNode if no path is specified");
        }

        this.at = at;
        this.value = value;
    }

    @Override
    public String[] getNode() {
        return this.at;
    }

    @Override
    public DataNode at(String nodePath) {
        SimpleDataNode node = new SimpleDataNode(null, combine(nodePath));
        this.children.add(node);
        return node;
    }

    @Override
    public Collection<DataNode> getChildren() {
        return this.children;
    }

    private String[] combine(String... array2) {
        String[] array = new String[this.at.length + array2.length];
        System.arraycopy(this.at, 0, array, 0, this.at.length);
        System.arraycopy(array2, 0, array, this.at.length, array2.length);
        return array;
    }

    public Optional<Object> get() {
        return Optional.ofNullable(this.value);
    }

    @Override
    public Optional<String> getString() {
        if (this.value == null) {
            return Optional.empty();
        }
        if (!(this.value instanceof String s)) {
            return Optional.empty();
        }
        return Optional.of(s);
    }

    @Override
    public Optional<Integer> getInt() {
        if (this.value == null) {
            return Optional.empty();
        }
        if (!(this.value instanceof Number s)) {
            return Optional.empty();
        }
        return Optional.of(s.intValue());
    }

    @Override
    public Optional<Double> getDouble() {
        if (this.value == null) {
            return Optional.empty();
        }
        if (!(this.value instanceof Number s)) {
            return Optional.empty();
        }
        return Optional.of(s.doubleValue());
    }

    @Override
    public Optional<Boolean> getBoolean() {
        if (this.value == null) {
            return Optional.empty();
        }
        if (!(this.value instanceof Boolean s)) {
            return Optional.empty();
        }
        return Optional.of(s);
    }

    @Override
    public List<DataNode> getCollection() {
        if (this.value == null) {
            return Collections.emptyList();
        }
        if (!(this.value instanceof Collection<?> c)) {
            return Collections.emptyList();
        }
        List<?> asList = new ArrayList<>(c);
        List<DataNode> list = new ArrayList<>(c.size());
        for (int A = 0; A < c.size(); A++) {
            list.add(new SimpleDataNode(asList.get(A), combine(A + "")));
        }
        return list;
    }

    @Override
    public void set(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Value: ").append(this.value.toString());
        this.children.forEach(child -> builder.append("\t").append(child.toString()).append("\n"));
        return builder.toString();
    }
}
