import java.io.*;
import java.util.Scanner;

public class Main implements Serializable {
    public static void main(String[] args) {
        String[] products = {"Молоко", "Хлеб", "Гречневая крупа"};
        int[] price = {50, 14, 80};
        String input;
        boolean isEnd = false;
        Basket basket;
        Scanner scanner = new Scanner(System.in);
        File file = new File("Basket.txt");
        
        if (file.exists()) {
            boolean fileIsExists = true;
            basket = new Basket(products, price, fileIsExists);
        } else {
            boolean fileIsExists = false;
            basket = new Basket(products, price, fileIsExists);
        }

        while (!isEnd) {
            System.out.println("Список возможных товаров для покупки");

            for (int i = 0; i < products.length; i++) {
                System.out.println((i + 1) + " " + products[i] + " " + price[i] + " руб/шт");
            }

            System.out.println("Выберите товар и количество или введите \"end\"");
            input = scanner.nextLine();

            if (input.equals("end")) {
                basket.printCart();
                isEnd = true;
            } else {

                try {
                    String[] parts = input.split(" ");
                    int productNum = Integer.parseInt(parts[0]);
                    int amount = Integer.parseInt(parts[1]);
                    basket.addToCart(productNum, amount);
                } catch (NumberFormatException nf) {
                    System.out.println("Введено некорректное значение");
                } catch (IndexOutOfBoundsException out) {
                    System.out.println("Введено некорректное количество значений");
                }
            }
        }
    }
}