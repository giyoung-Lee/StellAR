package com.ssafy.stellar.star.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ssafy.stellar.star.dto.response.PlanetDto;
import com.ssafy.stellar.star.dto.response.StarDto;
import com.ssafy.stellar.star.entity.PlanetEntity;
import com.ssafy.stellar.star.entity.StarEntity;
import com.ssafy.stellar.star.repository.PlanetRepository;
import com.ssafy.stellar.star.repository.StarRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import static java.lang.Math.*;

@Service
public class StarServiceImpl implements StarService{

    private final StarRepository starRepository;
    private final PlanetRepository planetRepository;

    @Value("${stellar.star.max-magv}")
    private Double starMaxMagv;

    @Value("${stellar.star.min-magv}")
    private Double starMinMagv;

    final static double longitude = 126.8526; // 예: 광주 경도
    final static double latitude = 35.1595; // 예: 광주 위도

    public StarServiceImpl(StarRepository starRepository,
                           PlanetRepository planetRepository) {
        this.starRepository = starRepository;
        this.planetRepository = planetRepository;
    }

    @Override
    public Map<String, Object> returnAllStar(String maxMagv) {
        List<StarEntity> list = starRepository.findAllByMagVLessThan(maxMagv);

        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();

        double minMagV = starMinMagv;
        double maxMagV = starMaxMagv;

        LocalDate startDate = LocalDate.of(2000, 1, 1);
        LocalDate today = LocalDate.now();
        long yearsBetween = ChronoUnit.YEARS.between(startDate, today);

        for (StarEntity star : list) {
            double pmRA = Double.parseDouble(star.getPMRA()); // 초(arcsec) 단위
            double pmDec = Double.parseDouble(star.getPMDEC()); // 초(arcsec) 단위

            double newRA = calculateNewRA(star.getRA(), pmRA, yearsBetween);
            double newDec = calculateNewDec(star.getDeclination(), pmDec, yearsBetween);
            double[] xyz = calculateXYZCoordinates(newRA, newDec);
            double normalizedMagV = 20000
                    + Math.exp((Double.parseDouble(star.getMagV()) - minMagV) * 2 / (maxMagV - minMagV));

            StarDto dto = getStarDto(star, xyz, normalizedMagV);
            JsonElement jsonElement = gson.toJsonTree(dto);
            jsonObject.add(star.getStarId(), jsonElement);
        }
        Map<String, Object> map = gson.fromJson(jsonObject, Map.class);
        return map;

    }

    @Override
    public List<PlanetDto> returnPlanet() {

        List<PlanetEntity> list = planetRepository.findAll();
        List<PlanetDto> result = new ArrayList<>();

        double minMagV = list.stream()
                .mapToDouble(planet -> Double.parseDouble(planet.getPlanetMagV()))
                .min()
                .orElse(Double.MAX_VALUE);
        double maxMagV = list.stream()
                .mapToDouble(planet -> Double.parseDouble(planet.getPlanetMagV()))
                .max()
                .orElse(Double.MIN_VALUE);

        for (PlanetEntity entity : list) {
            PlanetDto dto = new PlanetDto();

            dto.setPlanetId(entity.getPlanetId());
            dto.setPlanetRA(entity.getPlanetRA());
            dto.setPlanetDEC(entity.getPlanetDEC());
            dto.setPlanetMagV(entity.getPlanetMagV());

            double rate = (Double.parseDouble(entity.getPlanetMagV()) - minMagV) / (maxMagV - minMagV);
            double normalizedMagV = 20000
                    + Math.exp(rate * 2 / (starMaxMagv - starMinMagv));

            dto.setNomalizedMagV(normalizedMagV);

            String[] raParts = entity.getPlanetRA().split(" ");

            double ra_hours = Double.parseDouble(raParts[0]);
            double ra_minutes = Double.parseDouble(raParts[1]);
            double ra_seconds = Double.parseDouble(raParts[2]);

            double ra_degrees = (ra_hours * 15) + (ra_minutes / 4) + (ra_seconds / 240);

            boolean isNegative = entity.getPlanetDEC().startsWith("-");
            String[] decParts = entity.getPlanetDEC().substring(isNegative ? 1 : 0).split(" ");

            int buho = isNegative ? -1 : 1;
            double dec_degrees = Double.parseDouble(decParts[0]) * buho;
            double dec_minutes = Double.parseDouble(decParts[1]);
            double dec_seconds = Double.parseDouble(decParts[2]);

            dec_degrees += dec_minutes / 60 + dec_seconds / 3600;
            
//            ZonedDateTime now = ZonedDateTime.now();
//            System.out.println("now = " + now);
//
//            // 현재 시간을 UTC 시간대로 변환합니다.
//            ZonedDateTime utcNow = now.withZoneSameInstant(ZoneId.of("UTC"));
//
//            double lst = calculateLocalSiderealTime(longitude, utcNow);
//            double ha = calculateHourAngle(lst, ra_degrees);
//            double[] azAlt = calculateAzimuthAndAltitude(ha, dec_degrees, latitude);
//            System.out.println("entity = " + entity.getPlanetId());
//            System.out.println("Azimuth: " + azAlt[0] + " degrees");
//            System.out.println("Altitude: " + azAlt[1] + " degrees");
//            double[] xyz = calculateXYZCoordinates(azAlt[0], azAlt[1]);
            double[] xyz = calculateXYZCoordinates(ra_degrees, dec_degrees);
            dto.setCalX(xyz[0]);
            dto.setCalY(xyz[1]);
            dto.setCalZ(xyz[2]);

            result.add(dto);
        }

        return result;
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

            degrees += minutes / 60 + seconds / 3600 + pmDec / 3600000 * years;
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


    // 현지 항성시를 계산하는 메서드
    private static double calculateLocalSiderealTime(double longitude, ZonedDateTime dateTime) {
        double jd = dateTime.toLocalDate().toEpochDay() + 2440587.5 + (dateTime.toLocalTime().toSecondOfDay() / 86400.0);
        double t = (jd - 2451545.0) / 36525.0;
        double gst = (100.46061837 + 36000.770053608 * t + 0.000387933 * t * t - t * t * t / 38710000.0) % 360;
        double lst = (gst + longitude / 15) % 360; // 경도를 시간 단위로 변환
        System.out.println("gst = " + gst);
        System.out.println("lst = " + lst);
        return lst < 0 ? lst + 360 : lst;
    }

    // 시간각을 계산하는 메서드
    private static double calculateHourAngle(double lst, double ra) {
        double hourAngle = lst - ra;
        System.out.println("hourAngle = " + hourAngle);
        return hourAngle < 0 ? hourAngle + 360 : hourAngle;
    }

    // 방위각과 고도를 계산하는 메서드
    private static double[] calculateAzimuthAndAltitude(double ha, double dec, double latitude) {
        ha = toRadians(ha);
        System.out.println("ha = " + toDegrees(ha));
        dec = toRadians(dec);
        latitude = toRadians(latitude);

        double sinAltitude = sin(dec) * sin(latitude) + cos(dec) * cos(latitude) * cos(ha);
        double altitude = asin(sinAltitude);

        double cosAzimuth = (sin(dec) - sin(altitude) * sin(latitude)) / (cos(altitude) * cos(latitude));
        double azimuth = acos(cosAzimuth);

        // sin(ha)가 양수일 때, 방위각을 360에서 결과 값으로 빼줘야 정확한 방위각이 계산됨
        azimuth = sin(ha) > 0 ? 2 * PI - azimuth : azimuth;
        azimuth = toDegrees(azimuth);

        // 방위각이 음수일 경우 360을 더해주어 항상 양수로 만듦
        if (azimuth < 0) {
            azimuth += 360;
        }

        return new double[]{azimuth, toDegrees(altitude)};
    }


}
