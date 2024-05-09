package com.ssafy.stellar.utils.stars;
import com.ssafy.stellar.star.entity.StarEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class CalcStarLocation {

    @Value("${stellar.star.max-magv}")
    private Double starMaxMagv;

    @Value("${stellar.star.min-magv}")
    private Double starMinMagv;


    // J2000.0 기준 시각
    private static final Instant J2000 = LocalDateTime.of(2000, 1, 1, 12, 0).toInstant(ZoneOffset.UTC);
    // 태양년 기준 1년의 초 수 (365.25일)
    private static final double SECONDS_PER_YEAR = 365.25 * 24 * 60 * 60;

    public double calculateNewRA(String initialRA, double pmRA) {
        // 현재 시각을 Instant로 가져오기
        Instant now = Instant.now();
        // J2000.0 기준으로 현재까지 경과한 초 수 계산
        long secondsBetween = now.getEpochSecond() - J2000.getEpochSecond();
        // 초 단위 경과 시간을 연수로 변환
        double yearsBetween = secondsBetween / SECONDS_PER_YEAR;

        System.out.printf("J2000.0 기준으로 현재까지 %.9f년이 지났습니다.%n", yearsBetween);

        try {
            String[] raParts = initialRA.split(" ");
            if (raParts.length != 3) {
                System.out.println("Invalid RA format: " + initialRA);
                return 0;
            }
            double hours = Double.parseDouble(raParts[0]);
            double minutes = Double.parseDouble(raParts[1]);
            double seconds = Double.parseDouble(raParts[2]);

            return toSosu(hours, minutes, seconds, pmRA, yearsBetween);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing RA values: " + initialRA);
            return 0;
        }
    }

    public double calculateNewDec(String initialDec, double pmDec) {
        // 현재 시각을 Instant로 가져오기
        Instant now = Instant.now();
        // J2000.0 기준으로 현재까지 경과한 초 수 계산
        long secondsBetween = now.getEpochSecond() - J2000.getEpochSecond();
        // 초 단위 경과 시간을 연수로 변환
        double yearsBetween = secondsBetween / SECONDS_PER_YEAR;

        System.out.printf("J2000.0 기준으로 현재까지 %.9f년이 지났습니다.%n", yearsBetween);

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

            return toSosu(degrees, minutes, seconds, pmDec, yearsBetween);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing Dec values: " + initialDec);
            return 0;
        }
    }

    private double toSosu(double degrees, double minutes, double seconds, double pm, double yearsBetween) {
        return degrees + minutes / 60 + seconds / 3600 + pm / 3600000 * yearsBetween;
    }

    public double calculateNormalizedMagV(StarEntity star) {
        return 8000 * (1 + Math.exp((Double.parseDouble(star.getMagV()) - starMinMagv) * 1 / (starMaxMagv - starMinMagv)));
    }
}
