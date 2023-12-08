FROM openjdk:17
COPY storage-api/target/ /tmp/
WORKDIR /tmp/
EXPOSE $SERVER_PORT
#HEALTHCHECK --interval=60s --timeout=10s --start-period=30s \
#CMD curl --fail http://localhost:$SERVER_PORT/open/_healthcheck || exit 1
ENTRYPOINT exec java  $JAVA_OPTS -jar /tmp/*.jar
