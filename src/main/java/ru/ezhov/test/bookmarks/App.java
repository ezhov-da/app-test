package ru.ezhov.test.bookmarks;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        try {
//            List<BookmarksNode> bookmarksNodes = new BookmarksReader("D:/bookmarks.html").read();
            List<BookmarksNode> bookmarksNodes = new BookmarksReader("D://bookmarks-programmer.html").read();
//            bookmarksNodes.forEach(System.out::println);
            showTree(bookmarksNodes);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showTree(List<BookmarksNode> bookmarksNodes) {
        SwingUtilities.invokeLater(() ->
        {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Throwable ex) {
                //
            }
            JFrame frame = new JFrame("_________");

            Map<Integer, DefaultMutableTreeNode> map = new HashMap();
            DefaultMutableTreeNode root = null;
            DefaultMutableTreeNode defaultMutableTreeNode;
            for (BookmarksNode b : bookmarksNodes) {
                if (b.getParentId() == -1) {
                    root = new DefaultMutableTreeNode(b);
                    defaultMutableTreeNode = root;
                } else {
                    DefaultMutableTreeNode parent = map.get(b.getParentId());
                    defaultMutableTreeNode = new DefaultMutableTreeNode(b);
                    if (parent == null) {
                        root.add(defaultMutableTreeNode);
                    } else {
                        parent.add(defaultMutableTreeNode);
                    }
                }
                map.put(b.getId(), defaultMutableTreeNode);
            }

            JTree tree = new JTree(root);

            frame.add(new JScrollPane(tree));
            frame.setSize(1000, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

}
