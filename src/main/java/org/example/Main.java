package org.example;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Scanner;

public class Main implements Serializable {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String[] products = {"Молоко", "Хлеб", "Гречневая крупа"};
        int[] price = {50, 14, 80};
        String input;
        boolean isEnd = false;
        Basket basket = null;
        Scanner scanner = new Scanner(System.in);

        XMLData xmlData = new XMLData(); //XML
        xmlData.readXML();

        if (xmlData.getLoad()[0].equals("true")) {

            if (xmlData.getLoad()[2].equals("json")) {
                //Десериализация из .json файла
                try (FileInputStream fis = new FileInputStream(xmlData.getLoad()[1]);
                     ObjectInputStream ois = new ObjectInputStream(fis)) {
                    basket = (Basket) ois.readObject();
                } catch (Exception ex) {
                    System.out.println("ERR");
                }
            } else {
                basket = new Basket(products, price);
                File file = new File(xmlData.getSave()[1]);
                basket.loadFromTxtFile(file);
            }
        } else {
            basket = new Basket(products, price);
            xmlData.setLoadEnabled();
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

                    if (xmlData.getLog()[0].equals("true")) {
                        File file = new File(xmlData.getLog()[1]);
                        clientLog.log(productNum, amount, file); //Вызов метода log
                    }

                    if (xmlData.getSave()[0].equals("true")){

                        if (xmlData.getSave()[2].equals("json")) {

                            // Сериализация через .json файл
                            try (FileOutputStream fos = new FileOutputStream(xmlData.getSave()[1]);
                                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                                oos.writeObject(basket);
                            } catch (IOException e) {
                                System.out.println("Файл открыт");
                            }
                        } else {
                            File file = new File(xmlData.getSave()[1]);
                            basket.saveTxt(file);
                        }
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