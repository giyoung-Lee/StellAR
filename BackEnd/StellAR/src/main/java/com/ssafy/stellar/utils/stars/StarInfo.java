package com.ssafy.stellar.utils.stars;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StarInfo {
    private final StarRepository starRepository;

    @Autowired
    public StarInfo(StarRepository starRepository) {
        this.starRepository = starRepository;
    }
    public static List<String[]> readCSVToArray() throws IOException {
        List<String[]> results = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource("dump/converted_results.csv");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            // 첫 줄을 읽어서 건너뛰기
            reader.readLine();

            // 이후 줄들을 계속 읽기
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                results.add(data);
            }
        }
        return results;
    }

    public static List<String> processString(String input) {
        if (input.contains("|")) {
            String[] parts = input.split("\\|");
            return new ArrayList<>(Arrays.asList(parts));
        } else {
            List<String> list = new ArrayList<>();
            list.add(input);
            return list;
        }
    }

    public void saveStars(List<String[]> starList) {
        int count = 0;

        // 0.Identifier     1.RA            2.DEC           3.PMRA      4.PMDEC
        // 5.Mag_V          6.Parallax      7.SP_TYPE 	    8.Name      9.Constellation
        // 10.HD	        11.SAO	        12.HIP	        13.HR	    14.Gaia DR2
        // 15.Gaia DR3      16.Star_Type
        for (String[] star : starList) {
            count ++;
            Star starEntity = new Star();

            List<String> temp = null;
            // 이름이 비어있지 않다면
            if (!star[8].isEmpty()) {
                starEntity.setStarId(star[8]);

            } else if (!star[9].isEmpty()) { // 별자리 이름 비어있지 않다면
                // | 있는지 확인 해서 뜯어서 저장해 주고,
                temp = processString(star[9]);
                // 나머지 번호에 맞는 이름으로 해줌...
            } else if (!star[11].isEmpty()) {
                starEntity.setStarId(star[11]);
            } else if (!star[12].isEmpty()) {
                starEntity.setStarId(star[12]);
            } else if (!star[13].isEmpty()) {
                starEntity.setStarId(star[13]);
            } else if (!star[14].isEmpty()) {
                starEntity.setStarId(star[14]);
            } else if (!star[15].isEmpty()) {
                starEntity.setStarId(star[15]);
            }
            starEntity.setRA(star[1]);
            starEntity.setDeclination(star[2]);
            if (star[3] == null || star[3].equals("--") || star[3].isEmpty()) {
                starEntity.setPMRA("0");
                starEntity.setPMDEC("0");
            } else {
                starEntity.setPMRA(star[3]);
                starEntity.setPMDEC(star[4]);
            }

            if (star[5] == null || star[5].equals("--") || star[5].isEmpty()) {
                starEntity.setMagV("F");
            } else {
                starEntity.setMagV(star[5]);
            }

            starEntity.setParallax(star[6]);
            starEntity.setSP_TYPE(star[7]);
            starEntity.setHD(processString(star[10]).get(0));
            starEntity.setStarType(star[16]);

            if (temp != null) {
                for (String constellation : temp) {
                    starEntity.setConstellation(constellation);
                    starEntity.setStarId(constellation);
                    starRepository.save(starEntity);
                }
            } else {
                if (starEntity.getStarId() == null) {
                   starEntity.setStarId(star[0]);
                }
               starEntity.setConstellation("");
               starRepository.save(starEntity);
            }
            System.out.println(count + " / " + starList.size());
        }
    }

    // 별 정보 db에 넣을거면 주석을 풀고 StellarApplication을 한번 실행 하세요
//    @PostConstruct
//    public void init() {
//        try {
//            List<String[]> starData = readCSVToArray();
//            saveStars(starData);
//            System.out.println("Data successfully loaded and saved.");
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Error loading data from CSV.");
//        }
//    }
}

