package com.erlis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Payment {

    @Min(3)
    private int amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private int getTotalAmount(Order order) {
        return order.getOrderRows().stream().mapToInt(item -> item.getPrice() * item.getQuantity()).sum();
    }

    private List<LocalDate> getDatesBetweenAllMonths(LocalDate startDate, LocalDate endDate) {

        LocalDate startOfNextMonth = startDate.with(TemporalAdjusters.firstDayOfNextMonth());
        LinkedList<LocalDate> localDates = startOfNextMonth.datesUntil(endDate, Period.ofMonths(1))
                .collect(Collectors.toCollection(LinkedList::new));
        localDates.addFirst(startDate);
        localDates.addLast(endDate);
        return localDates;
    }

    public List<Payment> getAllPayments(Order order, LocalDate start, LocalDate end) {

        List<Payment> payments = new LinkedList<>();
        List<LocalDate> datesBetweenAllMonths = getDatesBetweenAllMonths(start, end);
        int monthsToPay = datesBetweenAllMonths.size();
        int amountToPayPerMonth = getTotalAmount(order) / monthsToPay;

        if (amountToPayPerMonth <= 2) {
            while (amountToPayPerMonth <= 2) {
                datesBetweenAllMonths.remove(datesBetweenAllMonths.size() - 1);
                monthsToPay = datesBetweenAllMonths.size();
                amountToPayPerMonth = getTotalAmount(order) / monthsToPay;
            }
        }
        int residue = getTotalAmount(order) % monthsToPay;
        for (int i = 0; i < monthsToPay; i++) {
            if (residue == 0) {
                Payment payment = new Payment();
                payment.setAmount(amountToPayPerMonth);
                payment.setDate(getDatesBetweenAllMonths(start, end).get(i));
                payments.add(payment);
            } else {
                Payment payment = new Payment();
                payment.setAmount(amountToPayPerMonth);
                payment.setDate(getDatesBetweenAllMonths(start, end).get(i));
                payments.add(payment);
                if (i == monthsToPay - 1) {
                    residue = getResidue(payments, residue);
                }
            }
        }
        return payments;
    }

    private int getResidue(List<Payment> payments, int residue) {
        for (int j = payments.size()-1; j > 0; j--) {
            payments.get(j).setAmount(payments.get(j).getAmount() + 1);
            residue -= 1;
            if (residue == 0) {
                break;
            }
        }
        return residue;
    }

}
