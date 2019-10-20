package com.erlis.model;

import lombok.Data;
import org.apache.commons.text.StringEscapeUtils;

import java.util.LinkedList;
import java.util.List;

@Data
public class Order {
    private Long id;
    private String orderNumber;

    private List<Item> orderRows;

    public List<Item> getOrderRows() {
        if (orderRows == null) {
            orderRows = new LinkedList<>();
        }
        return orderRows;
    }

    public void setOrderRows(List<Item> orderRows) {
        this.orderRows = orderRows;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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