package org.example;

import com.opencsv.CSVWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class ClientLog {
    private int[] productList;
    private Basket basket;

    public ClientLog(String[] products, Basket basket) {
        productList = new int[products.length];
        this.basket = basket;
    }

    protected void log(int productNum, int amount) {
        productList[productNum] += amount;
        JSONObject obj = new JSONObject();
        obj.put("productNum", productNum);
        obj.put("amount", amount);

        try (FileWriter fw = new FileWriter("data.json")) {
            fw.write(obj.toJSONString());
            fw.flush();
        } catch (IOException e) {
            System.out.println("Файл открыт");
        }

    }

    protected void exportAsCSV(File textFile) {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("data.json"));
            JSONObject jObj = (JSONObject) obj;
            JSONArray productNumArray = (JSONArray) jObj.get("productNum");
            JSONArray amountArray = (JSONArray) jObj.get("amount");
            String[] productNumList = new String[basket.getUsingCount() - 1];
            String[] amountList = new String[basket.getUsingCount() - 1];
            int i = 0;

            for (Object object : productNumArray) {
                productNumList[i] = (String) object;
                i++;
            }
            i = 0;

            for (Object object : amountArray) {
                amountList[i] = (String) object;
                i++;
            }
            String[] dataList = new String[basket.getUsingCount() - 1];

            for (int ii = 0; ii < dataList.length; ii++) {
                dataList[ii] = productNumList[ii] + "," + amountList[ii];
            }

            try (CSVWriter writer = new CSVWriter(new FileWriter("log.csv", true))) {

                for (String data : dataList) {
                    String[] parts = data.split(",");
                    writer.writeNext(parts);
                }
            } catch (IOException e) {
                System.out.println("Файл открыт");
            }
        } catch (ParseException e) {
            System.out.println("Ошибка парсинга");
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            System.out.println("Файл открыт");
        }
    }
}
