public class Basket {
    private String[] products;
    private int[] price;
    int[] productsInCart;

    public Basket (String[] products, int[] price) {
        this.products = products;
        this.price = price;
        productsInCart = new int[products.length];
    }

    public void addToCart (int productNum, int amount) {
        productsInCart[productNum - 1] = amount;
    }
}
