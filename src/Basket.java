import java.io.*;

public class Basket {
    private String[] products;
    private int[] price;
    private int[] productsInCart;
    private int cartPrice;
    private File basket;

    public Basket (String[] products, int[] price, boolean fileIsExists) {
        this.products = products;
        this.price = price;
        productsInCart = new int[products.length];

        if (fileIsExists) {
            loadFromBinFile(basket);
        }
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
        saveBin(basket);
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

    private File checkFile(File textFile) {
        textFile = new File("Basket.txt");

        if (!textFile.exists()) {

            try {
                textFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Файл открыт");
            }
        }

        return textFile;
    }

    public void saveTxt (File textFile) {
        File file = checkFile(textFile);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

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

    public void saveBin(File file) {

        try (FileOutputStream fos = new FileOutputStream("Basket.bin");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(file);
        } catch (IOException io) {
            System.out.println("Файл открыт");
        }
    }

    public static Basket loadFromBinFile(File file) {
        Basket loadedBasket = null;

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            loadedBasket = (Basket) ois.readObject();
        } catch (IOException io) {
            System.out.println("Файл открыт");
        } catch (ClassNotFoundException cnf) {
            System.out.println("ERR");
        }

        return loadedBasket;
    }
}