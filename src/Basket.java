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

    public Basket (String[] products, int[] price, int[] productsInCart, int cartPrice) {
        this.products = products;
        this.price = price;
        this.productsInCart = productsInCart;
        this.cartPrice = cartPrice;
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

        if (!textFile.exists()) {

            try {
                textFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Файл открыт");
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(textFile))) {

            for (int i = 0; i < products.length; i++) {
                bw.write(productsInCart[i]);
                bw.write(" ");
            }

            bw.newLine();
            bw.write(cartPrice);

            for (int i = 0; i < products.length; i++) {
                bw.write(products[i]);
                bw.write(" ");
            }

            for (int i = 0; i < products.length; i++) {
                bw.write(price[i]);
                bw.write(" ");
            }

            bw.flush();
        } catch (IOException e) {
            System.out.println("Save file: IO exception");
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
        String cartLine = null;
        String cartSum = null;
        String productsLine = null;
        String priceLine = null;

        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            cartLine = br.readLine();
            cartSum = br.readLine();
            productsLine = br.readLine();
            priceLine = br.readLine();
        } catch (IOException e) {
            System.out.println("Файл открыт");
        }

        String[] cartParts = cartLine.split(" ");
        String[] productsParts = productsLine.split(" ");
        String[] priceParts = priceLine.split(" ");

        int[] cart = new int[cartParts.length];
        String[] products = new String[productsParts.length];
        int[] price = new int[priceParts.length];

        int cartPrice = Integer.parseInt(cartSum);

        for (int i = 0; i < cartParts.length; i++) {
            cart[i] = Integer.parseInt(cartParts[i]);
            products[i] = productsParts[i];
            price[i] = Integer.parseInt(priceParts[i]);
        }

        return new Basket (products, price, cart, cartPrice);
    }
}