FROM maven:3.6 AS stage-build

COPY . /usr/src
RUN cd /usr/src && mvn clean package -U -DskipTests


FROM openjdk:11-jre

WORKDIR /app
COPY --from=stage-build /usr/src/target/bookshelf-manager-web-0.0.1-SNAPSHOT.jar web.jar

CMD ["sh", "-c", "java -jar /app/web.jar --spring.profiles.active=development"]
