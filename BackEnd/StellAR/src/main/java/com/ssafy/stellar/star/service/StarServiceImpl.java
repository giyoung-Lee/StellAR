package com.ssafy.stellar.star.service;

import com.ssafy.stellar.star.dto.response.StarReturnAllDto;
import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.star.repository.StarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class StarServiceImpl implements StarService{

    private final StarRepository starRepository;

    public StarServiceImpl(StarRepository starRepository) {
        this.starRepository = starRepository;
    }

    @Override
    public List<StarReturnAllDto> returnAllStar() {
        List<StarEntity> list = starRepository.findAll();
        List<StarReturnAllDto> result = new ArrayList<>();

        LocalDate startDate = LocalDate.of(2000, 1, 1);
        LocalDate today = LocalDate.now();
        long yearsBetween = ChronoUnit.YEARS.between(startDate, today);

        for (StarEntity star : list) {
            double pmRA = Double.parseDouble(star.getPMRA()); // 초(arcsec) 단위
            double pmDec = Double.parseDouble(star.getPMDEC()); // 초(arcsec) 단위

            String newRA = calculateNewRA(star.getRA(), pmRA, yearsBetween);
            String newDec = calculateNewDec(star.getDeclination(), pmDec, yearsBetween);
            double[] xyz = calculateXYZCoordinates(newRA, newDec);

            StarReturnAllDto dto = new StarReturnAllDto();
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

            result.add(dto);
        }

        return result;
    }

    private static String calculateNewRA(String initialRA, double pmRA, long years) {
        try {
            String[] raParts = initialRA.split(" ");
            if (raParts.length != 3) {
                System.out.println("Invalid RA format: " + initialRA);
                return "00 00 00.0000";  // 기본 값 반환
            }
            double hours = Double.parseDouble(raParts[0]);
            double minutes = Double.parseDouble(raParts[1]);
            double seconds = Double.parseDouble(raParts[2]);

            double totalSeconds = hours * 3600 + minutes * 60 + seconds;
            double newTotalSeconds = totalSeconds + pmRA * years;

            hours = Math.floor(newTotalSeconds / 3600);
            minutes = Math.floor((newTotalSeconds % 3600) / 60);
            seconds = newTotalSeconds % 60;

            return String.format("%02d %02d %.4f", (int)hours, (int)minutes, seconds);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing RA values: " + initialRA);
            return "00 00 00.0000";  // 기본 값 반환
        }
    }

    private static String calculateNewDec(String initialDec, double pmDec, long years) {
        try {
            boolean isNegative = initialDec.startsWith("-");
            String[] decParts = initialDec.substring(isNegative ? 1 : 0).split(" ");
            if (decParts.length != 3) {
                System.out.println("Invalid Dec format: " + initialDec);
                return "+00 00 00.000";  // 기본 값 반환
            }
            double degrees = Double.parseDouble(decParts[0]);
            double minutes = Double.parseDouble(decParts[1]);
            double seconds = Double.parseDouble(decParts[2]);

            double totalSeconds = Math.abs(degrees) * 3600 + minutes * 60 + seconds;
            if (isNegative) {
                totalSeconds = -totalSeconds;
            }
            double newTotalSeconds = totalSeconds + pmDec * years;

            isNegative = newTotalSeconds < 0;
            newTotalSeconds = Math.abs(newTotalSeconds);
            degrees = Math.floor(newTotalSeconds / 3600);
            minutes = Math.floor((newTotalSeconds % 3600) / 60);
            seconds = newTotalSeconds % 60;

            return String.format("%s%02d %02d %.3f", isNegative ? "-" : "+", (int)degrees, (int)minutes, seconds);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing Dec values: " + initialDec);
            return "+00 00 00.000";  // 기본 값 반환
        }
    }

    public static double[] calculateXYZCoordinates(String newRA, String newDec) {
        // RA와 Dec를 라디안으로 변환합니다.
        double raRadians = convertRaToRadians(newRA);
        double decRadians = convertDecToRadians(newDec);

        // 3차원 좌표를 계산합니다.
        double x = Math.cos(decRadians) * Math.cos(raRadians);
        double y = Math.cos(decRadians) * Math.sin(raRadians);
        double z = Math.sin(decRadians);

        return new double[]{x, y, z};
    }

    private static double convertRaToRadians(String ra) {
        String[] raParts = ra.split(" ");
        double degrees;
        // RA 형식을 확인하고 올바르게 파싱합니다.
        if (raParts.length >= 3) {
            double hours = Double.parseDouble(raParts[0]);
            double minutes = Double.parseDouble(raParts[1]);
            double seconds = Double.parseDouble(raParts[2]);
            degrees = hours * 15 + minutes * 15 / 60.0 + seconds * 15 / 3600.0;
        } else if (raParts.length == 2) {
            // 분까지만 있는 경우
            double hours = Double.parseDouble(raParts[0]);
            double minutes = Double.parseDouble(raParts[1]);
            degrees = hours * 15 + minutes * 15 / 60.0;
        } else if (raParts.length == 1) {
            // 시간만 있는 경우
            degrees = Double.parseDouble(raParts[0]) * 15;
        } else {
            throw new IllegalArgumentException("Invalid RA format: " + ra);
        }
        return Math.toRadians(degrees);
    }

    private static double convertDecToRadians(String dec) {
        // Dec는 도 단위로 주어지므로, 직접 라디안으로 변환합니다.
        boolean isNegative = dec.startsWith("-");
        String[] decParts = dec.split(" ");
        double degrees;
        // Dec 형식을 확인하고 올바르게 파싱합니다.
        if (decParts.length >= 3) {
            degrees = Double.parseDouble(decParts[0]);
            double arcMinutes = Double.parseDouble(decParts[1]);
            double arcSeconds = Double.parseDouble(decParts[2]);
            degrees += (arcMinutes / 60.0) + (arcSeconds / 3600.0);
        } else if (decParts.length == 2) {
            // 분까지만 있는 경우
            degrees = Double.parseDouble(decParts[0]);
            double arcMinutes = Double.parseDouble(decParts[1]);
            degrees += (arcMinutes / 60.0);
        } else if (decParts.length == 1) {
            // 도만 있는 경우
            degrees = Double.parseDouble(decParts[0]);
        } else {
            throw new IllegalArgumentException("Invalid Dec format: " + dec);
        }

        // 부호를 처리합니다.
        return Math.toRadians(degrees);
    }

}
