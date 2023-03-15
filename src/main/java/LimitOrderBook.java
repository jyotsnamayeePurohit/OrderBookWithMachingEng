import java.util.*;

public class LimitOrderBook {
    // TreeMap to store orders at different price levels
    private Map<Double, LinkedList<OrderBook>> buyOrders = new TreeMap<>(Collections.reverseOrder()); // descending order
    private Map<Double, LinkedList<OrderBook>> sellOrders = new TreeMap<>();

    // Add an order to the order book
    public void addOrder(OrderBook order) {
        Map<Double, LinkedList<OrderBook>> orders = (order.getSide() == Side.BUY) ? buyOrders : sellOrders;
        LinkedList<OrderBook> list = orders.computeIfAbsent(order.getPrice(), k -> new LinkedList<>());
        list.add(order);
    }

    // Delete an order from the order book by order id
    public void deleteOrder(int orderId) {
        for (LinkedList<OrderBook> orders : buyOrders.values()) {
            orders.removeIf(order -> order.getId() == orderId);
        }
        for (LinkedList<OrderBook> orders : sellOrders.values()) {
            orders.removeIf(order -> order.getId() == orderId);
        }
    }

    // Modify an order in the order book by order id and new quantity
    public void modifyOrder(int orderId, int newQuantity) {
        for (LinkedList<OrderBook> orders : buyOrders.values()) {
            for (OrderBook order : orders) {
                if (order.getId() == orderId) {
                    order.setQuantity(newQuantity);
                    orders.remove(order); // remove from the current price level
                    addOrder(order); // re-add to the order book to maintain order by price & priority
                    return;
                }
            }
        }
        for (LinkedList<OrderBook> orders : sellOrders.values()) {
            for (OrderBook order : orders) {
                if (order.getId() == orderId) {
                    order.setQuantity(newQuantity);
                    orders.remove(order); // remove from the current price level
                    addOrder(order); // re-add to the order book to maintain order by price & priority
                    return;
                }
            }
        }
    }

    // Get all orders at a given price level and side, ordered by priority
    public List<OrderBook> getOrders(double price, Side side) {
        Map<Double, LinkedList<OrderBook>> orders = (side == Side.BUY) ? buyOrders : sellOrders;
        LinkedList<OrderBook> list = orders.get(price);
        return (list != null) ? new ArrayList<>(list) : Collections.emptyList();
    }


    public void process(OrderBook order) {
        // Check if the order is a buy or sell
        if (order.getSide() == Side.BUY) {
            // Try to match the order with buy orders in the order book
            List<OrderBook> sellOrders = getOrders(order.getPrice(), Side.BUY);
            Integer amount = order.getQuantity();
            for (OrderBook sellOrder : sellOrders) {

                Integer balanceAmount = sellOrder.getQuantity();

                if (amount >= balanceAmount) {
                    amount = amount - balanceAmount;
                    sellOrder.setQuantity(0);
                    modifyOrder((int) sellOrder.getId(), sellOrder.getQuantity());
                } else {
                    amount = balanceAmount - amount;
                    sellOrder.setQuantity(amount);
                    modifyOrder((int) sellOrder.getId(), sellOrder.getQuantity());
                }

            }
        } else {


            // Try to match the order with sell orders in the order book
            List<OrderBook> sellOrders = getOrders(order.getPrice(), Side.SELL);
            Integer amount = order.getQuantity();
            for (OrderBook sellOrder : sellOrders) {

                Integer balanceAmount = sellOrder.getQuantity();

                if (amount >= balanceAmount) {
                    amount = amount - balanceAmount;
                    sellOrder.setQuantity(0);
                    modifyOrder((int) sellOrder.getId(), sellOrder.getQuantity());
                } else {
                    amount = balanceAmount - amount;
                    sellOrder.setQuantity(amount);
                    modifyOrder((int) sellOrder.getId(), sellOrder.getQuantity());
                }

            }
        }
    }

}
