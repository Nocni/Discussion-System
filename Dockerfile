FROM openjdk:11
COPY . /app
WORKDIR /app
CMD ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "-cp", "target/Projekat-1.0-SNAPSHOT.jar:lib/*", "rs.raf.DiscussionServer", "/tmp/server", "account", "127.0.0.1:8090", "127.0.0.1:8090,127.0.0.1:8091,127.0.0.1:8092", "8090"]