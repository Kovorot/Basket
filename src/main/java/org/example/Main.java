package org.example;

import java.io.*;
import java.util.Scanner;

public class Main implements Serializable {
    public static void main(String[] args) {
        String[] products = {"Молоко", "Хлеб", "Гречневая крупа"};
        int[] price = {50, 14, 80};
        String input;
        boolean isEnd = false;
        Basket basket = null;
        Scanner scanner = new Scanner(System.in);
        File file = new File("Basket.json");

        if (file.exists()) {

            //Десериализация из .json файла
            try (FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                basket = (Basket) ois.readObject();
            } catch (Exception ex) {
                System.out.println("ERR");
            }
        } else {
            basket = new Basket(products, price);
        }
        ClientLog clientLog = new ClientLog(products, basket);

        while (!isEnd) {
            System.out.println("Список возможных товаров для покупки");

            for (int i = 0; i < products.length; i++) {
                System.out.println((i + 1) + " " + products[i] + " " + price[i] + " руб/шт");
            }

            System.out.println("Выберите товар и количество или введите \"end\"");
            input = scanner.nextLine();

            if (input.equals("end")) {
                basket.printCart();
                clientLog.exportAsCSV(new File("data.json")); //Вызов метода exportAsCSV
                isEnd = true;
            } else {
                try {
                    String[] parts = input.split(" ");
                    int productNum = Integer.parseInt(parts[0]);
                    int amount = Integer.parseInt(parts[1]);
                    basket.addToCart(productNum, amount);
                    clientLog.log(productNum, amount); //Вызов метода log

                    // Сериализация через .json файл
                    try (FileOutputStream fos = new FileOutputStream(file);
                         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                        oos.writeObject(basket);
                    } catch (IOException e) {
                        System.out.println("Файл открыт");
                    }
                } catch (NumberFormatException nf) {
                    System.out.println("Введено некорректное значение");
                } catch (IndexOutOfBoundsException out) {
                    System.out.println("Введено некорректное количество значений");
                }
            }
        }
    }
}