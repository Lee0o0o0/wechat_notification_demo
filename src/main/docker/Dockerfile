FROM java:8
EXPOSE 8080
VOLUME /tmp
ADD wechatnotificationdemo-0.0.1-SNAPSHOT.jar wechatnotificationdemo.jar
RUN sh -c 'touch /wechatnotificationdemo.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /wechatnotificationdemo.jar" ]