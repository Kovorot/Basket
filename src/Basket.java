public class Basket {
    private String[] products;
    private int[] price;
    private int[] productsInCart;
    private int cartPrice;

    public Basket (String[] products, int[] price) {
        this.products = products;
        this.price = price;
        productsInCart = new int[products.length];
    }

    public void addToCart (int productNum, int amount) {
        productsInCart[productNum - 1] += amount;
        cartPrice += price[productNum] * amount;
    }

    public void printCart() {
        System.out.println("Ваша корзина:");

        for (int i = 0; i < products.length; i++) {

            if (productsInCart[i] != 0) {
                int fullPrice = price[i] * productsInCart[i];
                System.out.println(products[i] + " " + productsInCart[i] + " шт " + price[i] + " руб/шт " + fullPrice + " руб в сумме");
                System.out.println("Итого " + cartPrice + " руб");
            }
    }
}
