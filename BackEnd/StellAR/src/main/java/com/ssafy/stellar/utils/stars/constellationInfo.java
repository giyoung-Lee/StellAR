package com.ssafy.stellar.utils.stars;

import com.ssafy.stellar.constellation.entity.ConstellationLinkEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class constellationInfo {

    private final constellationUtilRepository constellationUtilRepository;

    @Autowired
    public constellationInfo(constellationUtilRepository constellationUtilRepository) {
        this.constellationUtilRepository = constellationUtilRepository;
    }

    public static List<String[]> readCSVToArray() throws IOException {
        List<String[]> results = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource("dump/constellation.csv");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            // 이후 줄들을 계속 읽기
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                results.add(data);
            }
        }
        return results;
    }


    public void saveConstellation() throws IOException {
        List<String[]> list = readCSVToArray();
        List<String[]> failedSaves = new ArrayList<>();

        for (String[] star : list) {
            try {
                ConstellationLinkEntity constellation = new ConstellationLinkEntity();
                constellation.setConstellationId(star[0]);
                constellation.setStarA(star[1]);
                constellation.setStarB(star[2]);

                constellationUtilRepository.save(constellation);
            } catch (Exception e) {
                // 예외가 발생했을 경우 실패한 star 배열을 failedSaves 리스트에 추가
                failedSaves.add(star);
                // 에러 로깅 또는 추가적인 예외 처리를 여기에 작성할 수 있습니다.
            }
        }

        // 실패한 저장 목록을 처리하는 코드, 필요에 따라 추가 작성
        for (String[] fail : failedSaves) {
            System.out.println(fail[0] + " " + fail[1] + " " + fail[2] + " ");
        }
    }

//    @PostConstruct
//    public void init() {
//        try {
//            saveConstellation();
//            System.out.println("Data successfully loaded and saved.");
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Error loading data from CSV.");
//        }
//    }

}
