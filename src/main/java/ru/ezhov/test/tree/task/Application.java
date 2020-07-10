package ru.ezhov.test.tree.task;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args) throws IOException {
        List<BinaryObject> binaryObjects = Arrays.asList(
                new BinaryObject(103, "Дополнительная выкладка", null),
                new BinaryObject(100, "Лояльность Хаммер", 103),
                new BinaryObject(101, "Хаммер", 103),
                new BinaryObject(111, "Хаммер child", 101),
                new BinaryObject(123, "ЮХУ", null)
        );

        Map<Integer, TreePresenter> treePresenterMap = new HashMap<>();

        List<TreePresenter> parentPresenters = new ArrayList<>();

        for (BinaryObject binaryObject : binaryObjects) {
            final Integer prnId = binaryObject.getPrnId();
            if (prnId == null) {
                final TreePresenter treePresenterParent = new TreePresenter(binaryObject.getId(), binaryObject.getName());
                treePresenterMap.put(binaryObject.getId(), treePresenterParent);
                parentPresenters.add(treePresenterParent);
            } else {
                final TreePresenter treePresenterParent = treePresenterMap.get(prnId);
                if (treePresenterParent != null) {
                    final TreePresenter presenter = new TreePresenter(binaryObject.getId(), binaryObject.getName());
                    treePresenterParent.add(presenter);
                    treePresenterMap.put(binaryObject.getId(), presenter);
                } else {
                    TreePresenter treePresenterParentNew = new TreePresenter(binaryObject.getId(), binaryObject.getName());
                    treePresenterMap.put(binaryObject.getId(), treePresenterParentNew);
                    parentPresenters.add(treePresenterParentNew);
                }
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(parentPresenters));
    }
}

class BinaryObject {
    private final int id;
    private final String name;
    private final Integer prnId;

    public BinaryObject(int id, String name, Integer prnId) {
        this.id = id;
        this.name = name;
        this.prnId = prnId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrnId() {
        return prnId;
    }

    @Override
    public String toString() {
        return "BinaryObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", prnId=" + prnId +
                '}';
    }
}

class TreePresenter {
    private final int id;
    private final String text;
    private boolean checked = false;
    private boolean expanded = false;
    private boolean leaf = true;

    private List<TreePresenter> data = new ArrayList<>();

    public TreePresenter(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isChecked() {
        return checked;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void add(TreePresenter treePresenter) {
        data.add(treePresenter);
        this.leaf = data.isEmpty();
    }

    public List<TreePresenter> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "TreePresenter{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", checked=" + checked +
                ", expanded=" + expanded +
                ", leaf=" + leaf +
                ", data=" + data +
                '}';
    }
}