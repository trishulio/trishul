FROM openjdk:17 AS prod

ARG JAR_FILE=./target/app-1.0.0.jar
ARG USER_ID=johndoe
ARG UUID=8877

# cd /opt/app
WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

RUN useradd -ms /bin/bash -u ${UUID} ${USER_ID} && \
    chmod 0500 app.jar && \
    chown ${USER_ID} app.jar

USER ${USER_ID}

EXPOSE 8080
ENTRYPOINT [ "java" ]
CMD [ "-Xmx1g", "-jar", "app.jar" ]