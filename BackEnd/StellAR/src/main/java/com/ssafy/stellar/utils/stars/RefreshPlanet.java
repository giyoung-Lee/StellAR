package com.ssafy.stellar.utils.stars;

import com.ssafy.stellar.star.entity.PlanetEntity;
import com.ssafy.stellar.star.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class RefreshPlanet {

    @Autowired
    private final PlanetRepository planetRepository;

    public RefreshPlanet(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    public void returnPlanet() {

        String[] identifiers = {"199", "299", "499", "599", "699", "799", "899", "301", "10"};
        String[] names = {"Mercury", "Venus", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Moon", "Sun"};

        Map<String, Map<String, String>> planetData = fetchCelestialData(identifiers, names);

        planetData.forEach((name, data) -> {
            PlanetEntity planet = new PlanetEntity();
            planet.setPlanetId(name); // Assuming the planet name is used as the ID
            planet.setPlanetDEC(data.get("DEC"));
            planet.setPlanetMagV(data.get("mag_v"));
            planet.setPlanetRA(data.get("RA"));

            planetRepository.save(planet);
        });
    }

    private static Map<String, Map<String, String>> fetchCelestialData(String[] identifiers, String[] names) {
        Map<String, Map<String, String>> dataMap = new HashMap<>();
        for (int i = 0; i < identifiers.length; i++) {
            String data = fetchDataForBody(identifiers[i]);
            Map<String, String> parsedData = parseData(data, names[i]);
            dataMap.put(names[i], parsedData);
        }
        return dataMap;
    }

    private static String fetchDataForBody(String body) {
        String BASE_URL = "https://ssd.jpl.nasa.gov/horizons_batch.cgi?batch=1";
        try {
            ZonedDateTime now = ZonedDateTime.now();
            String startDateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String endDateTime = now.plusMinutes(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String query = String.format("&COMMAND='%s'&START_TIME='%s'&STOP_TIME='%s'&STEP_SIZE='1 d'&QUANTITIES='1,9,20,23,24'&CSV_FORMAT='YES'", body, startDateTime, endDateTime);
            URL url = new URL(BASE_URL + query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine).append("\n");
                }
                in.close();
                return response.toString();
            } else {
                return "Error: Failed to fetch data";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private static Map<String, String> parseData(String data, String name) {
        Map<String, String> parsedData = new HashMap<>();
        String[] lines = data.split("\n");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains("$$SOE")) {
                String[] details = lines[i+1].split(",");
                parsedData.put("NAME", name);
                parsedData.put("RA", details[3].trim());
                parsedData.put("DEC", details[4].trim());
                parsedData.put("mag_v", details[5].trim());
            }
        }
        return parsedData;
    }

    //별 정보 db에 넣을거면 주석을 풀고 StellarApplication을 한번 실행 하세요
    // https://ssd.jpl.nasa.gov/horizons/app.html#/ api 참고
//    @PostConstruct
//    @Scheduled(fixedDelay = 10000)
<<<<<<< Updated upstream
//    @Scheduled(cron = " 0 * * * * * ")
=======
//    @Scheduled(cron = "0 * * * * *")
>>>>>>> Stashed changes
    public void init() {
        try {
            returnPlanet();
            System.out.println("Data successfully uploaded and saved.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error uploading data");
        }
    }

}
