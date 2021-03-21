FROM gradle:jdk11 AS BUILD
WORKDIR /testDocker
COPY ./praktikumsplaner /testDocker
RUN gradle bootJar

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /testDocker
COPY --from=BUILD /testDocker/build/libs/*.jar \
                  /testDocker/praktikumsplaner.jar
COPY --from=BUILD /testDocker/key.der /testDocker

EXPOSE 8080
CMD java -jar praktikumsplaner.jar
