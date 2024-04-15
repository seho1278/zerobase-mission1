package test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonParserTest {
    public static void main(String[] args) {
        // JSON 데이터
        String json = OpenApi.get(1, 1);

        // Gson 객체 생성
        Gson gson = new Gson();

        // JsonParser 객체 생성
        JsonParser parser = new JsonParser();

        // JsonParser를 사용하여 JsonElement로 JSON 파싱
        JsonElement element = parser.parse(json);

        // JsonElement를 보기 좋게 출력
        System.out.println(gson.toJson(element));
    }
}
