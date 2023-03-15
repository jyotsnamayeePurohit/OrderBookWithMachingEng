public class OrderBook {
   private static long nextId = 0;

    private  long id;
    private  double price;
    private  int quantity;
    private  Side side;
    private final long time;


   /* private static long nextId = 0;

    private final long id;
    private final double price;
    private final int quantity;
    private final Side side;
    private final long time;*/


    public OrderBook(double price, int quantity, Side side) {
        this.id = nextId++;
        this.price = price;
        this.quantity = quantity;
        this.side = side;
        this.time = System.currentTimeMillis();
    }

    public OrderBook(long id, double price, int quantity, Side side) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.side = side;
        this.time = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

   /* public void setId(long id) {
        this.id = id;
    }*/

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Side getSide() {
        return side;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getTime() {
        return time;
    }
}
