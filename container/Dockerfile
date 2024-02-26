# 第一个阶段：使用 Maven 构建
FROM g-ziod8129-docker.pkg.coding.net/mysterious-forest/docker/cn-base-maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY ../pom.xml .
RUN mvn -s /usr/share/maven/ref/settings.xml dependency:go-offline
COPY ../src src
RUN mvn -s /usr/share/maven/ref/settings.xml -DskipTests=true package

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/cn-authorization-center.jar /app/cn-authorization-center.jar
EXPOSE 9000
CMD ["java", "-jar", "cn-authorization-center.jar"]
