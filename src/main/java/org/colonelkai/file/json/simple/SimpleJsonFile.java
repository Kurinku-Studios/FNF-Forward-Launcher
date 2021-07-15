package org.colonelkai.file.json.simple;

import org.colonelkai.file.DataFile;
import org.colonelkai.file.DataNode;
import org.colonelkai.file.simple.SimpleDataNode;
import org.colonelkai.file.simple.SimpleRootDataNode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SimpleJsonFile implements DataFile {
    @Override
    public String build(DataNode root) {
        Object simpleJson = buildRecursive(root);
        if (simpleJson instanceof JSONObject o) {
            return o.toJSONString();
        }
        if (simpleJson instanceof JSONArray a) {
            return a.toJSONString();
        }
        throw new IllegalArgumentException("Unable to parse " + root);
    }

    private Object buildRecursive(DataNode node) {
        Map<String, Object> children = new HashMap<>();
        JSONObject jsonObj = new JSONObject();
        for (DataNode child : node.getChildren()) {
            if (!(child instanceof SimpleDataNode sdn)) {
                continue;
            }
            Optional<Object> opValue = sdn.get();
            if (opValue.isEmpty()) {
                Object child2 = buildRecursive(child);
                String[] childNode = child.getNode();
                children.put(childNode[node.getNode().length], child2);
                continue;
            }
            Object value = opValue.get();
            String key = child.getNode()[node.getNode().length];
            children.put(key, value);
        }
        jsonObj.putAll(children);
        return jsonObj;
    }

    @Override
    public SimpleRootDataNode generateFrom() {
        return new SimpleRootDataNode();
    }

    @Override
    public DataNode generateFrom(String contents) {
        Object parsed = JSONValue.parse(contents);
        SimpleRootDataNode rootNode = new SimpleRootDataNode();
        generateRecursive(rootNode, "", parsed);
        return rootNode;
    }

    private DataNode generateRecursive(DataNode parent, String key, Object target) {
        if (!(target instanceof JSONObject o)) {
            throw new IllegalArgumentException("Target must be a object");
        }
        DataNode simpleDataNode = parent.at(key);
        o.entrySet().forEach(e -> {
            if (!(e instanceof Map.Entry<?, ?> entry)) {
                throw new RuntimeException("EntrySet did not return Entry");
            }
            try {
                generateRecursive(simpleDataNode, entry.getKey().toString(), entry.getValue());
            } catch (IllegalArgumentException ignore) {
                DataNode valueData = simpleDataNode.at(entry.getKey().toString());
                valueData.set(entry.getValue());
            }
        });
        return simpleDataNode;
    }

    @Override
    public String[] expectedFileTypes() {
        return new String[]{".json"};
    }
}
