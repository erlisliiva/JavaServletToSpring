package com.erlis.dao;

import com.erlis.model.Item;
import com.erlis.model.Order;
import com.erlis.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDAO {
    private JdbcTemplate template;

    @Autowired
    public OrderDAO(JdbcTemplate template) {
        this.template = template;
    }

    public List<Order> getOrders() throws SQLException {

        String sqlStatement = FileUtil.readFileFromClasspath("data.sql");
        List<Map<String, Object>> rows = template.queryForList(sqlStatement);
        Map<Object, Order> map = new HashMap<>();
        for (Map row : rows) {

            if (map.containsKey(row.get("id"))) {
                Order order = map.get(row.get("id"));
                if (row.get("orderfk") != null) {
                    order.getOrderRows().add(new Item(
                            (String) row.get("name"),
                            (Integer) row.get("quantity"),
                            (Integer) row.get("price")
                    ));
                }
            } else {
                Order order = new Order();
                setObject(row, order);
                map.put(row.get("id"), order);

            }
        }
        return new LinkedList<>(map.values());
    }

    public Order postOrder(Order order) {

        var data = new BeanPropertySqlParameterSource(order);
        String sqlStatementForRwos = FileUtil.readFileFromClasspath("postRowsSql.sql");
        Number orders = new SimpleJdbcInsert(template)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(data);

        order.setId(orders.longValue());
        for (Item item : order.getOrderRows()) {
            template.update(sqlStatementForRwos, orders, item.getItemName(), item.getQuantity(), item.getPrice());
        }
        return order;
    }

    public void deleteOrder(int id) {

        String sql = String.format("delete from orders where id = %s", id);
        template.update(sql);
    }

    public Order getOrderById(int id) {

        String sqlStatement = FileUtil.readFileFromClasspath("getOrderByIdSql.sql");

        List<Map<String, Object>> rows = template.queryForList(sqlStatement, id);
        Order order = new Order();
        for (Map row : rows) {
            setObject(row, order);
        }
        return order;
    }
    private void setObject(Map row, Order order) {
        order.setId((Long) row.get("id"));
        order.setOrderNumber((String) row.get("ordernumber"));
        if (row.get("orderfk") != null) {
            order.getOrderRows().add(new Item(
                    (String) row.get("name"),
                    (Integer) row.get("quantity"),
                    (Integer) row.get("price")
            ));
        }
    }
}
