import java.io.*;

public class Basket {
    private String[] products;
    private int[] price;
    private int[] productsInCart;
    private int cartPrice;
    private File basket;

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

    public void SaveTxt (File textFile) {
        textFile = new File("Basket.txt");

        try {
            textFile.createNewFile();
        } catch (IOException e) {
            System.out.println("Файл открыт");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(textFile))) {

            for (int i = 0; i < products.length; i++) {
                bw.write(productsInCart[i]);
                bw.write(" ");
            }

            bw.newLine();
            bw.write(cartPrice);
        } catch (IOException e) {
            System.out.println("Save file: IO exception");
        }
    }
}