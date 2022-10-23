package com.example.projectkaveretaplication;

public class Product {
    private String id;
    private String product_name;
    private int price;
    private int quantity;
    private String category;
    private String url_image;


    public Product(String id, String product_name, int price, int quantity, String category, String url_image) {
        this.id = id;
        this.product_name = product_name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.url_image = url_image;
    }

    public String  getId() {
        return id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getUrl_image() {
        return url_image;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", product_name='" + product_name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", url_image='" + url_image + '\'' +
                '}';
    }
}
