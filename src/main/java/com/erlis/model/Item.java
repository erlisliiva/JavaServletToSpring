package com.erlis.model;

import lombok.Data;
import org.apache.commons.text.StringEscapeUtils;

@Data
public class Item {


    private String itemName;
    private int quantity;
    private int price;

    public Item(String itemName, Integer quantity, Integer price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public Item() {

    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "{ "
                + "\"itemName\":\"" + StringEscapeUtils.escapeJava(itemName) + "\""
                + ", \"quantity\":\"" + quantity + "\""
                + ", \"price\":\"" + price + "\""
                + " }";
    }

}
