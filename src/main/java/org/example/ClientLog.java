package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ClientLog {
    private int[] productList;

    public ClientLog(String[] products) {
        productList = new int[products.length];
    }

    protected void log (int productNum, int amount) {
        productList[productNum] += amount;
        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();

        for (int i = 0; i < productList.length; i++) {
            list.add(productList[i]);
        }
        obj.put("productList", list);

        try (FileWriter fw = new FileWriter("data.json")) {
            fw.write(obj.toJSONString());
            fw.flush();
        } catch (IOException e) {
            System.out.println("Файл открыт");
        }
    }

    protected void exportAsCSV (File textFile) {

    }
}
