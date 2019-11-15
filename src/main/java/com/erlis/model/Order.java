package com.erlis.model;

import lombok.Data;
import org.apache.commons.text.StringEscapeUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

@Data
public class Order {

    private Long id;

    @NotNull
    @Size(min = 2, max = 500)
    private String orderNumber;

    @Valid
    private List<Item> orderRows;

    public List<Item> getOrderRows() {
        if (orderRows == null) {
            orderRows = new LinkedList<>();
        }
        return orderRows;
    }

    @Override
    public String toString() {
        return "{ "
                + "\"id\": \"" + id + "\""
                + ", \"orderNumber\": \"" + StringEscapeUtils.escapeJava(orderNumber) + "\""
                + ", \"orderRows\": " + orderRows
                + " }";
    }

}