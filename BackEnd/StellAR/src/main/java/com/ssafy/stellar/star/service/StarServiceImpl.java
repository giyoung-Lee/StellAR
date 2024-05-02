package com.ssafy.stellar.star.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ssafy.stellar.star.dto.response.StarDto;
import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.star.repository.StarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
public class StarServiceImpl implements StarService{

    private final StarRepository starRepository;

    public StarServiceImpl(StarRepository starRepository) {
        this.starRepository = starRepository;
    }

    @Override
    public Map<String, Object> returnAllStar() {
        List<StarEntity> list = starRepository.findAll();

        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        double minMagV = list.stream()
                .mapToDouble(star -> Double.parseDouble(star.getMagV()))
                .min()
                .orElse(Double.MAX_VALUE);
        double maxMagV = list.stream()
                .mapToDouble(star -> Double.parseDouble(star.getMagV()))
                .max()
                .orElse(Double.MIN_VALUE);

        LocalDate startDate = LocalDate.of(2000, 1, 1);
        LocalDate today = LocalDate.now();
        long yearsBetween = ChronoUnit.YEARS.between(startDate, today);

        for (StarEntity star : list) {
            double pmRA = Double.parseDouble(star.getPMRA()); // 초(arcsec) 단위
            double pmDec = Double.parseDouble(star.getPMDEC()); // 초(arcsec) 단위

            double newRA = calculateNewRA(star.getRA(), pmRA, yearsBetween);
            double newDec = calculateNewDec(star.getDeclination(), pmDec, yearsBetween);
            double[] xyz = calculateXYZCoordinates(newRA, newDec);
            double normalizedMagV = 10000 + (Double.parseDouble(star.getMagV()) - minMagV) * (50000 - 10000) / (maxMagV - minMagV);

            StarDto dto = getStarDto(star, xyz, normalizedMagV);

            JsonElement jsonElement = gson.toJsonTree(dto);
            jsonObject.add(star.getStarId(), jsonElement);
        }
        Map<String, Object> map = gson.fromJson(jsonObject, Map.class);
        return map;

    }

    private static double calculateNewRA(String initialRA, double pmRA, long years) {
        try {
            String[] raParts = initialRA.split(" ");
            if (raParts.length != 3) {
                System.out.println("Invalid RA format: " + initialRA);
                return 0;
            }
            double hours = Double.parseDouble(raParts[0]);
            double minutes = Double.parseDouble(raParts[1]);
            double seconds = Double.parseDouble(raParts[2]);

            double degrees = hours * 15 + minutes / 4 + seconds / 240 + pmRA / 3600000 * years;
            return degrees;
        } catch (NumberFormatException e) {
            System.out.println("Error parsing RA values: " + initialRA);
            return 0;
        }
    }

    private static double calculateNewDec(String initialDec, double pmDec, long years) {
        try {
            boolean isNegative = initialDec.startsWith("-");
            String[] decParts = initialDec.substring(isNegative ? 1 : 0).split(" ");
            if (decParts.length != 3) {
                System.out.println("Invalid Dec format: " + initialDec);
                return 0;
            }
            int buho = isNegative ? -1 : 1;
            double degrees = Double.parseDouble(decParts[0]) * buho;
            double minutes = Double.parseDouble(decParts[1]);
            double seconds = Double.parseDouble(decParts[2]);

            degrees += minutes / 60 + seconds / 3600;
            return degrees;
        } catch (NumberFormatException e) {
            System.out.println("Error parsing Dec values: " + initialDec);
            return 0;
        }
    }

    public static double[] calculateXYZCoordinates(double newRA, double newDec) {
        // RA와 Dec를 라디안으로 변환합니다.
        double raRadians = convertRaToRadians(newRA);
        double decRadians = convertDecToRadians(newDec);
        // 3차원 좌표를 계산합니다.
        double x = Math.cos(decRadians) * Math.cos(raRadians);
        double y = Math.cos(decRadians) * Math.sin(raRadians);
        double z = Math.sin(decRadians);

        return new double[]{x, y, z};
    }

    private static double convertRaToRadians(double ra) {
        return Math.toRadians(ra);
    }

    private static double convertDecToRadians(double dec) {
        return Math.toRadians(dec);
    }

    private static StarDto getStarDto(StarEntity star, double[] xyz, double nomaliedMagV) {
        StarDto dto = new StarDto();
        dto.setStarId(star.getStarId());
        dto.setStarType(star.getStarType());
        dto.setCalX(xyz[0]);
        dto.setCalY(xyz[1]);
        dto.setCalZ(xyz[2]);
        dto.setConstellation(star.getConstellation());
        dto.setParallax(star.getParallax());
        dto.setSpType(star.getSP_TYPE());
        dto.setHd(star.getHD());
        dto.setMagV(star.getMagV());
        dto.setNomalizedMagV(nomaliedMagV);
        dto.setRA(star.getRA());
        dto.setDeclination(star.getDeclination());
        dto.setPMRA(star.getPMRA());
        dto.setPMDEC(star.getPMDEC());
        return dto;
    }

}
