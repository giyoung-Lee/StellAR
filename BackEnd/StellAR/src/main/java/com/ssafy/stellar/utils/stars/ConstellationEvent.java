package com.ssafy.stellar.utils.stars;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.stellar.constellation.entity.ConstellationEventEntity;
import com.ssafy.stellar.constellation.repository.ConstellationEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ConstellationEvent {

    private final ConstellationEventRepository constellationEventRepository;


    @Autowired
    public ConstellationEvent(ConstellationEventRepository constellationEventRepository) {
        this.constellationEventRepository = constellationEventRepository;
    }

    public void setConstellationEvent(String solYear, String solMonth) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/AstroEventInfoService/getAstroEventInfo");
        String serviceKey = "7rSvJsJaDzEpXvHwc0tU3Bw632hxbarb1FJ4WqaF/SgUWRC9H/kXWPTSXZEqVzMkpcl+eb/Qnnk3uop4OCRENg==";
        urlBuilder.append("?" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("50", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(serviceKey, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("solYear","UTF-8") + "=" + URLEncoder.encode(solYear, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("solMonth","UTF-8") + "=" + URLEncoder.encode(solMonth, "UTF-8"));
        urlBuilder.append("&_type=json"); // JSON 타입으로 명시적 요청

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json"); // JSON 응답을 명시적으로 요청

        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        String jsonData = sb.toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 파싱을 위해 JsonParser 객체 생성
        JsonElement rootElement = JsonParser.parseString(jsonData);
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonObject response = rootObject.getAsJsonObject("response");
        JsonObject body = response.getAsJsonObject("body");
        JsonObject items = body.getAsJsonObject("items");
        JsonArray itemArray = items.getAsJsonArray("item");

        // 배열의 각 요소를 처리
        for (JsonElement itemElement : itemArray) {
            JsonObject item = itemElement.getAsJsonObject();
            String astroEvent = item.get("astroEvent").getAsString();
            String astroTime = item.get("astroTime").getAsString();
            String temp_locdate = item.get("locdate").getAsString();

            if (!astroTime.isEmpty()) {
                LocalDate locdate = LocalDate.parse(temp_locdate, formatter);
                ConstellationEventEntity entity = new ConstellationEventEntity();
                entity.setAstroEvent(astroEvent);
                entity.setAstroTime(astroTime);
                entity.setLocdate(locdate);

                constellationEventRepository.save(entity);
            }
        }
    }

    // 특정 월의 데이터를 임의로 넣고싶다면 아래 어노테이션을 주석을 풀고,
    // setConstellationEvent 에 직접 값을 입력
    //  @PostConstruct
    @Scheduled(cron = "0 0 0 1 * ?")
    public void init() {
        try {
            LocalDate today = LocalDate.now();
            // 오늘로부터 1달 후의 날짜 계산
            LocalDate nextMonth = today.plusMonths(1);

            // 년도와 월을 문자열로 추출
            String year = String.valueOf(nextMonth.getYear());
            String month = String.format("%02d", nextMonth.getMonthValue());

            setConstellationEvent(year, month);
            System.out.println("Data successfully loaded and saved.");
        } catch (Exception e) {
            System.err.println("Failed to initialize constellation events: " + e.getMessage());
        }
    }
}
