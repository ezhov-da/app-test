package ru.ezhov.test.bookmarks;

class BookmarksNode {
    private int id;
    private int parentId;
    private String title;
    private String link;
    private String description;
    private Type type;

    public static BookmarksNode folder(int id, int parentId, String title) {
        BookmarksNode bookmarksNode = new BookmarksNode();
        bookmarksNode.id = id;
        bookmarksNode.parentId = parentId;
        bookmarksNode.title = title;
        bookmarksNode.type = Type.FOLDER;
        return bookmarksNode;
    }

    public static BookmarksNode url(int id, int parentId, String title, String link) {
        BookmarksNode bookmarksNode = new BookmarksNode();
        bookmarksNode.id = id;
        bookmarksNode.parentId = parentId;
        bookmarksNode.title = title;
        bookmarksNode.link = link;
        bookmarksNode.type = Type.URL;
        return bookmarksNode;
    }

    public int getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public Type getType() {
        return type;
    }

    public BookmarksNode setDescription(String description) {
        this.description = description;
        return this;
    }

    public String fullInfo() {
        return "BookmarksNode{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public String toString() {
        return title;
    }
}
