package ru.ezhov.test.bookmarks;

import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class BookmarksReader {
    private String pathToFile;

    public BookmarksReader(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public List<BookmarksNode> read() throws Exception {
        String DL_OPEN = "<DL>";
        String DL_CLOSE = "</DL>";
        String H3 = "<H3";
        String DT = "<DT>";
        String DD = "<DD>";

        Stack<BookmarksNode> stackParent = new Stack<>();
        List<BookmarksNode> bookmarksNodes = new ArrayList<>();
        int counterId = 0;
        BookmarksNode root = BookmarksNode.folder(counterId, -1, "root");
        bookmarksNodes.add(root);
        BookmarksNode lastBookmarksNode = root;

        try (Scanner scanner = new Scanner(new File(pathToFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
//                System.out.println(line);
                if (line.contains(DL_OPEN)) {
                    stackParent.push(lastBookmarksNode);
                } else if (line.contains(DL_CLOSE)) {
                    stackParent.pop();
                } else if (line.contains(DT) & line.contains(H3)) {
                    BookmarksNode bookmarksNodeParent = stackParent.peek();
                    String clearLine = replace(line, DT, "");
                    String convert = clearLine.replaceAll("&", "&amp;");
                    XPath xPath = XPathFactory.newInstance().newXPath();
                    String title = xPath.evaluate("//H3", new InputSource(new StringReader(convert)));
                    BookmarksNode bookmarksNode = BookmarksNode.folder(++counterId, bookmarksNodeParent.getId(), title);
                    lastBookmarksNode = bookmarksNode;
                    bookmarksNodes.add(bookmarksNode);
                } else if (line.contains(DT)) {
                    String clearLine = replace(line, DT, "");
                    int charIndexAHref;
                    if ((charIndexAHref = clearLine.indexOf("<A HREF")) != -1) {
                        BookmarksNode bookmarksNodeParent = stackParent.peek();
                        String aHref = clearLine.substring(charIndexAHref);
                        XPath xPath = XPathFactory.newInstance().newXPath();
                        String convert = aHref.replaceAll("&", "&amp;");
                        String href = xPath.evaluate("//A/@HREF", new InputSource(new StringReader(convert)));
                        String title = xPath.evaluate("//A", new InputSource(new StringReader(convert)));
                        BookmarksNode bookmarksNode = BookmarksNode.url(++counterId, bookmarksNodeParent.getId(), title, href);
                        lastBookmarksNode = bookmarksNode;
                        bookmarksNodes.add(bookmarksNode);
                    } else if ((charIndexAHref = clearLine.indexOf("<A FEEDURL")) != -1) {
                        BookmarksNode bookmarksNodeParent = stackParent.peek();
                        String aHref = clearLine.substring(charIndexAHref);
                        XPath xPath = XPathFactory.newInstance().newXPath();
                        String convert = aHref.replaceAll("&", "&amp;");
                        String href = xPath.evaluate("//A/@FEEDURL", new InputSource(new StringReader(convert)));
                        String title = xPath.evaluate("//A", new InputSource(new StringReader(convert)));
                        BookmarksNode bookmarksNode = BookmarksNode.url(++counterId, bookmarksNodeParent.getId(), title, href);
                        lastBookmarksNode = bookmarksNode;
                        bookmarksNodes.add(bookmarksNode);
                    } else {
                        throw new IllegalArgumentException("DT charIndexAHref " + charIndexAHref);
                    }
                } else if (line.contains(DD)) {
                    //TODO: доделать описание
                }
            }
            return bookmarksNodes;
        }
    }

    private static String replace(String source, String target, String value) {
        return source.replaceAll(target, value);
    }
}