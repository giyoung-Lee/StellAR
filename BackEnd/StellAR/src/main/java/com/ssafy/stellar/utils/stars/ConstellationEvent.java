package com.ssafy.stellar.utils.stars;

import com.ssafy.stellar.constellation.repository.ConstellationEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

@Service
public class ConstellationEvent {

    private final ConstellationEventRepository constellationEventRepository;


    @Autowired
    public ConstellationEvent(ConstellationEventRepository constellationEventRepository) {
        this.constellationEventRepository = constellationEventRepository;
    }

    public void setConstellationEvent(String solYear, String solMonth) throws IOException {

        String urlTemplate = "http://apis.data.go.kr/B090041/openapi/service/" +
                "AstroEventInfoService/getAstroEventInfo?" +
                "serviceKey=7rSvJsJaDzEpXvHwc0tU3Bw632hxbarb1FJ4WqaF%2FSgUWRC9H%2FkXWPTSXZEqVzMkpcl%2Beb%2FQnnk3uop4OCRENg%3D%3D" +
                "&solYear=" + solYear + "&solMonth=" + solMonth + "&_type=json";

        URL url = new URL(urlTemplate);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Map<String, Object> response = restTemplate.getForObject(urlTemplate, Map.class);

        System.out.println(response);
    }


//    @PostConstruct
//    public void init() {
//        try {
//            LocalDate today = LocalDate.now();
//            String year = String.valueOf(today.getYear());
//            String month = String.format("%02d", today.getMonthValue());
//
//            setConstellationEvent(year, month);
//            System.out.println("Data successfully loaded and saved.");
//        } catch (Exception e) {
//            System.err.println("Failed to initialize constellation events: " + e.getMessage());
//        }
//    }
}
