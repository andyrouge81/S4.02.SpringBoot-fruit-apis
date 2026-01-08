
# BUILD STAGE

FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiamos solo el pom primero para aprovechar cache
COPY pom.xml .

# Descargamos dependencias (cacheable)
RUN mvn dependency:go-offline

# Copiamos el resto del código
COPY src ./src

# Compilamos y generamos el JAR
RUN mvn clean package -DskipTests


#  RUNTIME STAGE

FROM eclipse-temurin:21-jre

WORKDIR /app

# Copiamos solo el JAR desde el stage anterior
COPY --from=build /app/target/*.jar app.jar

# Puerto por defecto de Spring Boot
EXPOSE 8080

# Ejecutamos la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
