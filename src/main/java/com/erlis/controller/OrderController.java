package com.erlis.controller;

import com.erlis.dao.OrderDAO;
import com.erlis.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    private final OrderDAO orderDao;

    @Autowired
    public OrderController(OrderDAO orderDao) {
        this.orderDao = orderDao;
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderDao.getOrders();
    }

    @DeleteMapping(path = "/{id}")
    public void deleteOrder(@PathVariable int id) {
        orderDao.deleteOrder(id);
    }

    @PostMapping
    public Order postOrder(@RequestBody @Valid Order order) {
        return orderDao.postOrder(order);
    }

    @GetMapping(path = "/{id}")
    public Order getOrderById(@PathVariable int id) {
        return orderDao.getOrderById(id);
    }
}
