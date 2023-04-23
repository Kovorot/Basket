import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] products = {"Молоко", "Хлеб", "Гречневая крупа"};
        int[] price = {50, 14, 80};
        int[] productsInCart = new int[products.length];
        int cartPrice = 0;
        String input;
        boolean isEnd = false;
        Scanner scanner = new Scanner(System.in);

        while (!isEnd) {
            System.out.println("Список возможных товаров для покупки");

            for (int i = 0; i < products.length; i++) {
                System.out.println((i + 1) + " " + products[i] + " " + price[i] + " руб/шт");
            }

            System.out.println("Выберите товар и количество или введите \"end\"");
            input = scanner.nextLine();

            if (input.equals("end")) {
                System.out.println("Ваша корзина:");

                for (int i = 0; i < products.length; i++) {

                    if (productsInCart[i] != 0) {
                        int fullPrice = price[i] * productsInCart[i];
                        System.out.println(products[i] + " " + productsInCart[i] + " шт " + price[i] + " руб/шт " + fullPrice + " руб в сумме");
                        System.out.println("Итого " + cartPrice + " руб");
                    }
                }
                isEnd = true;
            } else {

                try {
                    String[] parts = input.split(" ");
                    productsInCart[Integer.parseInt(parts[0]) - 1] += Integer.parseInt(parts[1]);
                    cartPrice += (price[Integer.parseInt(parts[0]) - 1]) * Integer.parseInt(parts[1]);
                } catch (NumberFormatException nf) {
                    System.out.println("Введено некорректное значение");
                } catch (IndexOutOfBoundsException iout) {
                    System.out.println("Введено некорректное количество значений");
                }
            }
        }
    }
}