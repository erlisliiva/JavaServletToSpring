SELECT orders.id, ordernumber, orderfk, name, quantity, price
from orders
         left join orderrows on orders.id = orderrows.orderFK;