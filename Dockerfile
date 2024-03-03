# 第一个阶段：使用 Maven 构建
FROM g-ziod8129-docker.pkg.coding.net/mysterious-forest/docker/cn-base-maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -s /usr/share/maven/ref/settings.xml dependency:go-offline
COPY src src
RUN mvn -s /usr/share/maven/ref/settings.xml -DskipTests=true package

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/cn-authorization-center.jar /app/cn-authorization-center.jar
# ENV 设置的环境变量在运行容器时能使用
ENV DB_HOST=172.17.0.2 DB_PORT=5432 DB_USERNAME=postgres
EXPOSE 9000
# ENV、RUN 都只能在构建容器时执行，在容器运行时不执行，因此无法用 ENV 来动态获取容器 IP，因此放到 CMD 中
CMD ["java", "-jar", "cn-authorization-center.jar"]