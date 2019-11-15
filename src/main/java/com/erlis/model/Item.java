package com.erlis.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class Item {


    @NotNull
    @Size(min = 2, max = 500)
    private String itemName;

    @NotNull
    @Min(1)
    private int quantity;

    @NotNull
    @Min(1)
    private int price;

    public Item(String itemName, Integer quantity, Integer price) {
        this.itemName = itemName;
        this.quantity = quantity;
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
