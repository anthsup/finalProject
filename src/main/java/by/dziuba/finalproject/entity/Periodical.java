package by.dziuba.subscription.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Periodical {
    private int id;
    private String title;
    private BigDecimal price;
    private int periodicityId;
    private int authorId;
    private int typeId;
    private String coverImage;
    private String description;
    private int booksAmount;

    public int getBooksAmount() {
        return booksAmount;
    }

    public void setBooksAmount(int booksAmount) {
        this.booksAmount = booksAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getPeriodicityId() {
        return periodicityId;
    }

    public void setPeriodicityId(int periodicityId) {
        this.periodicityId = periodicityId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Periodical that = (Periodical) o;
        return id == that.id &&
                periodicityId == that.periodicityId &&
                authorId == that.authorId &&
                typeId == that.typeId &&
                booksAmount == that.booksAmount &&
                Objects.equals(title, that.title) &&
                Objects.equals(price, that.price) &&
                Objects.equals(coverImage, that.coverImage) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, periodicityId, typeId, coverImage, description);
    }
    //TODO equals hashcode when done with fields
}
