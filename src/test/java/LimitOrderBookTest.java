import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static org.junit.Assert.*;

public class LimitOrderBookTest {


    private LimitOrderBook lob;
    private OrderBook book;

    @Before
    public void setUp() {
        lob = new LimitOrderBook();
    }



   @Test
  public   void testAddOrder() {
       LimitOrderBook lob = new LimitOrderBook();
        OrderBook order1 = new OrderBook(1, 10.0, 100, Side.BUY);
        OrderBook order2 = new OrderBook(2, 10.0, 200, Side.BUY);
        OrderBook order3 = new OrderBook(3, 11.0, 300, Side.SELL);
        lob.addOrder(order1);
        lob.addOrder(order2);
        lob.addOrder(order3);
        List<OrderBook> buyOrders = lob.getOrders(10.0, Side.BUY);
        List<OrderBook> sellOrders = lob.getOrders(11.0, Side.SELL);
        assertEquals(2, buyOrders.size());
        assertEquals(1, sellOrders.size());
        assertEquals(order2, buyOrders.get(1));
        assertEquals(order1, buyOrders.get(0));
        assertEquals(order3, sellOrders.get(0));


    }
    @Test
    public void testDeleteOrder() {
        LimitOrderBook lob = new LimitOrderBook();
        OrderBook order1 = new OrderBook(1, 10.0, 100, Side.BUY);
        OrderBook order2 = new OrderBook(2, 10.0, 200, Side.BUY);
        OrderBook order3 = new OrderBook(3, 11.0, 300, Side.SELL);
        lob.addOrder(order1);
        lob.addOrder(order2);
        lob.addOrder(order3);
        lob.deleteOrder(2);
        List<OrderBook> buyOrders = lob.getOrders(10.0, Side.BUY);
        List<OrderBook> sellOrders = lob.getOrders(11.0, Side.SELL);
        assertEquals(1, buyOrders.size());
        assertEquals(1, sellOrders.size());
        assertEquals(order1, buyOrders.get(0));
        assertEquals(order3, sellOrders.get(0));
    }

    @Test
    public void testModifyOrder() {
        LimitOrderBook lob = new LimitOrderBook();
        OrderBook order1 = new OrderBook(1, 10.0, 100, Side.BUY);
        OrderBook order2 = new OrderBook(2, 10.0, 200, Side.BUY);
        OrderBook order3 = new OrderBook(3, 11.0, 300, Side.SELL);
        lob.addOrder(order1);
        lob.addOrder(order2);
        lob.addOrder(order3);
        lob.modifyOrder(2, 150);
        List<OrderBook> buyOrders = lob.getOrders(10.0, Side.BUY);
        List<OrderBook> sellOrders = lob.getOrders(11.0, Side.SELL);
        assertEquals(2, buyOrders.size());
        assertEquals(1, sellOrders.size());
        assertEquals(order2, buyOrders.get(1));
        assertEquals(150, buyOrders.get(1).getQuantity());
        assertEquals(order3, sellOrders.get(0));
    }

    @Test
    public void testMatchingEngine() {
        LimitOrderBook lob = new LimitOrderBook();
        OrderBook buyOrder1 = new OrderBook(1, 9, 40, Side.BUY);
        OrderBook buyOrder2 = new OrderBook(2, 9, 20, Side.BUY);
        OrderBook sellOrder1 = new OrderBook(3, 8, 30, Side.BUY);
        OrderBook sellOrder2 = new OrderBook(4, 8, 20, Side.BUY);
        OrderBook buyOrder3 = new OrderBook(5, 9, 55, Side.BUY);

        lob.addOrder(buyOrder1);
        lob.addOrder(buyOrder2);
        lob.process(buyOrder3);
        assertEquals(0, buyOrder1.getQuantity());
        assertEquals(5, buyOrder2.getQuantity());

    }




}