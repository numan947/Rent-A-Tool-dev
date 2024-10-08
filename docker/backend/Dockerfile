# Build the backend application
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests


# Runtime stage
FROM amazoncorretto:22.0.2-alpine3.20
ARG PROFILE=prod
ARG APP_VERSION=1.0.0

WORKDIR /app
COPY --from=build /build/target/rent-a-tool-*.jar /app/

# Extract the JAR version
RUN APP_VERSION=$(ls /app | grep *.jar | awk 'NR==2{split($0,a,"-"); print a[3]}' | awk '{sub(/.jar$/,"")}1')\
    && echo "Building container with Rent-A-Tool v-$version"
EXPOSE 8088

ENV DB_URL=jdbc:postgresql://postgres-sql-rat:5432/rent_a_tool_db
ENV MAILDEV_URL=localhost

ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}

ENV EMAIL_HOST_NAME=missing_host_name
ENV EMAIL_USER_NAME=missing_host_name
ENV EMAIL_PASSWORD=missing_host_name

CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL}  rent-a-tool-${JAR_VERSION}.jar