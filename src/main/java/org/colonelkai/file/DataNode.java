package org.colonelkai.file;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DataNode {

    String[] getNode();

    DataNode at(String nodePath);

    default DataNode at(String nodePath, Object value) {
        DataNode node = at(nodePath);
        node.set(value);
        return node;
    }

    Collection<DataNode> getChildren();

    Optional<String> getString();

    Optional<Integer> getInt();

    Optional<Double> getDouble();

    Optional<Boolean> getBoolean();

    List<DataNode> getCollection();

    void set(Object value);

}
