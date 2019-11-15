package com.erlis.controller;

import com.erlis.dao.OrderDAO;
import com.erlis.model.Order;
import com.erlis.model.Payment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("orders")
public class DateController {

    private final OrderDAO orderDao;

    @Autowired
    public DateController(OrderDAO orderDao) {
        this.orderDao = orderDao;
    }

    @Data
    public static class SampleDto {
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate date = LocalDate.now();
    }

    @GetMapping("date")
    public SampleDto getDtoWithDateField() {
        return new SampleDto();
    }

    @GetMapping(path = "/{id}/installments")
    public List<Payment> getInstallments(@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(name = "start") LocalDate start,
                                         @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(name = "end") LocalDate end,
                                         @PathVariable int id) {

        Payment payment = new Payment();
        Order orderById = orderDao.getOrderById(id);
        return payment.getAllPayments(orderById, start, end);
    }

}