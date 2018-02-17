package edu.unl.task.android.shoppinglist.database.model;

public class ShoppingItem {

    private long id;
    private String title;

    public ShoppingItem(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
