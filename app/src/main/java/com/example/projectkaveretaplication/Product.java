package com.example.projectkaveretaplication;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String product_name;
    private int price;
    private int quantity;
    private String category;
    private String url_image;
    private int stock;

    public Product(String id, String product_name, int price, int quantity, String category, String url_image) {
        this.id = id;
        this.product_name = product_name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.url_image = url_image;
        this.stock = 10;
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


    public void setId(String id) {
        this.id = id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
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

    /*
    this functions for sent json data with the fields of Product.
     */


    public JSONObject getJSONObjectAddProduct() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", id);
            obj.put("productName", product_name);
            obj.put("price", price);
            obj.put("quantity", quantity);
            obj.put("category", category);
            obj.put("stock", stock);
            obj.put("productImgUrl", url_image);
            obj.put("action", "add");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public JSONObject getJSONObjectRemoveProduct() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", id);
            obj.put("productName", product_name);
            obj.put("price", price);
            obj.put("quantity", quantity);
            obj.put("category", category);
            obj.put("stock", stock);
            obj.put("productImgUrl", url_image);
            obj.put("action", "remove");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
