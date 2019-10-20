package com.erlis.servlet;

import com.erlis.dao.OrderDAO;
import com.erlis.model.Order;
import com.erlis.model.ValidationError;
import com.erlis.model.ValidationErrors;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/orders")
public class OrderServlet extends HttpServlet {

    private OrderDAO orderDao;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                (AnnotationConfigApplicationContext) context.getAttribute("context");
        orderDao = annotationConfigApplicationContext.getBean(OrderDAO.class);
    }


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Content-Type", "application/json");
        if (request.getParameter("id") == null) {
            try {
                List<Order> list = orderDao.getOrders();
                response.getWriter().print(list);
            } catch (SQLException e) {
                log(e.getMessage());
            }
        } else {

            Order orderViaId = orderDao.getOrderById(Integer.parseInt(request.getParameter("id")));
            if (orderViaId == null) {
                response.getWriter().print((String) null);
            } else {
                response.getWriter().print(orderViaId.toString());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String json = Util.readStream(req.getInputStream());
        Order order = new ObjectMapper().readValue(json, Order.class);
        if (order.getOrderNumber().length() < 2) {
            ValidationErrors validationError = new ValidationErrors();
            resp.setHeader("Content-Type", "application/json");
            resp.setStatus(400);
            validationError.getErrors().add(new ValidationError("too_short_number"));

            resp.getWriter().print(validationError.toString());
        } else {
            Order postedOrder = orderDao.postOrder(order);
            resp.setHeader("Content-Type", "application/json");
            resp.getWriter().print(postedOrder.toString());
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (req.getParameterMap().containsKey("id")) {
            orderDao.deleteOrder(Integer.parseInt(req.getParameter("id")));
        }
        resp.getWriter().print("Deleted: " + req.getParameter("id"));

    }
}

