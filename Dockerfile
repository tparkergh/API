FROM cwds/javajdk
RUN mkdir /opt/cws-api
RUN mkdir /opt/cws-api/logs
ADD config/api.yml /opt/cws-api/api.yml
ADD config/shiro.ini /opt/cws-api/config/shiro.ini
ADD config/enc.jceks /opt/cws-api/config/enc.jceks
ADD build/libs/api-dist.jar /opt/cws-api/api.jar
EXPOSE 8080
WORKDIR /opt/cws-api
CMD ["java", "-jar", "api.jar","server","api.yml"]
