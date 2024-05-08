package com.ssafy.stellar.utils.stars;

import com.ssafy.stellar.constellation.repository.ConstellationEventRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Map;

@Service
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
        System.out.println(sb.toString());
    }

    @PostConstruct
    public void init() {
        try {
            LocalDate today = LocalDate.now();
            String year = String.valueOf(today.getYear());
            String month = String.format("%02d", today.getMonthValue());

            setConstellationEvent(year, month);
            System.out.println("Data successfully loaded and saved.");
        } catch (Exception e) {
            System.err.println("Failed to initialize constellation events: " + e.getMessage());
        }
    }
}
