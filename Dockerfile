FROM markhobson/maven-chrome:jdk-17

ENV LANG es_ES.UTF-8
ENV LANGUAGE es_ES:en
ENV LC_ALL es_ES.UTF-8

#Copy source
COPY pom.xml pom.xml
RUN mvn -e -B dependency:resolve
COPY src  /src
ENTRYPOINT  ["mvn", "verify", "-PallTests"]