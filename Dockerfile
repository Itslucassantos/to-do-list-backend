FROM  amazoncorretto:17-alpine-jdk AS build
WORKDIR /app
COPY . .
RUN ["chmod", "+x", "./mvnw"]
RUN ./mvnw clean package -DskipTests

FROM --platform=linux/amd64 amazoncorretto:17-alpine-jdk
#VOLUME /tmp
ARG JAR_FILE=/app/target/task-list-api.jar
COPY --from=build ${JAR_FILE} task-list-api.jar
COPY --from=build /app/entrypoint.sh .

EXPOSE 8082

RUN ["chmod", "+x", "./entrypoint.sh"]
ENTRYPOINT ["./entrypoint.sh"]