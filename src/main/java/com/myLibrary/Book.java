package com.myLibrary;

public class Book {

    private Long id;
    private String title;
    private String Description;
    private String isbn;
    private float unitCost;

    public Book(Long id, String title, String description, String isbn, float unitCost) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setIsbn(isbn);
        setUnitCost(unitCost);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public float getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(float unitCost) {
        this.unitCost = unitCost;
    }
}
