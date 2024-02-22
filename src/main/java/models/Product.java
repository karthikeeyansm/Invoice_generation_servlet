package models;

import java.time.LocalDate;
import java.util.Date;

public class Product {
    private int product_id;
    private String name;
    private String description;
    private float price;
    private int stocks_available;
    private long createdAt;
    private long modifiedAt;

    public Product(){

    }
    public Product(int product_id, String name, String description, float price, int stocks_available, long createdAt, long modifiedAt) {
        this.product_id = product_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stocks_available = stocks_available;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStocks_available() {
        return stocks_available;
    }

    public void setStocks_available(int stocks_available) {
        this.stocks_available = stocks_available;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(long modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public String toString() {
        return this.product_id + "\t" + this.name + '\t' + this.description + '\t' + this.price + '\t' + this.stocks_available;
    }
}
