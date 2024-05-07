FROM gradle:8.5-jdk17 AS builder
WORKDIR /build

# 그래들 파일이 변경되었을 때만 새롭게 의존패키지 다운로드 받게함.
COPY /build.gradle /settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

# 빌더 이미지에서 애플리케이션 빌드
COPY ./ /build
RUN gradle build -x test --parallel

# APP
FROM openjdk:17
WORKDIR /app

# 빌더 이미지에서 jar 파일만 복사
# COPY --from=builder /build/build/libs/*-SNAPSHOT.jar ./app.jar
COPY --from=builder /build/build/libs/StellAR-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

# 로그 파일 폴더 만들어주고, 사용권한 넣기
RUN mkdir -p /app/logs && chown nobody:nobody /app/logs

# root 대신 nobody 권한으로 실행
USER nobody
ENTRYPOINT [                                                \
   "java",                                                 \
   "-jar",                                                 \
   "StellAR-0.0.1-SNAPSHOT.jar"             \
]
