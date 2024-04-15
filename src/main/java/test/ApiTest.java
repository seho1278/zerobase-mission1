package test;

import com.google.gson.Gson;
import zerobase.wifi.dto.OpenApiResponse;
import zerobase.wifi.dto.Row;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiTest {
    public static void main(String[] args) {
        String json1 = OpenApi.get(1, 1000);

        Gson gson = new Gson();
        // Json 문자열을 Java 객체로 변환

        OpenApiResponse response1 = gson.fromJson(json1, OpenApiResponse.class);

        int total = response1.getTbPublicWifiInfo().getList_total_count();

        System.out.println(total);

//        List<Row> allRows = new ArrayList<>();
//        for (int page = 1; page <= total / 1000 + 1 ; page++) {
//            String json = OpenApi.get(page, 1000);
//            OpenApiResponse response = gson.fromJson(json, OpenApiResponse.class);
//            Row[] currentPageRows = response.getTbPublicWifiInfo().getRow();
//            System.out.println("Page " + page + " rows: " + currentPageRows.length);
//            allRows.addAll(Arrays.asList(currentPageRows));
//        }
//
//        System.out.println("Total rows in allPages: " + allRows.size());
//
//        List<Row> allRows = new ArrayList<>();
//        for (int page = 1; page <= total / 1000 + 1 ; page++) {
//            String json = OpenApi.get(page, 1000);
//            OpenApiResponse response = gson.fromJson(json, OpenApiResponse.class);
//            allRows.addAll(Arrays.asList(response.getTbPublicWifiInfo().getRow()));
//        }

//        System.out.println(allRows.size());
    }
}
