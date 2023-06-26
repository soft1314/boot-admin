# 基础镜像
FROM  openjdk:8-jre
# 作者
MAINTAINER soft1314
# 创建目录
RUN mkdir -p /home/myapp
# 挂载目录
VOLUME /home/myapp
# 指定路径
WORKDIR /home/myapp
# 复制jar文件到路径
COPY ./target/log6144-0.0.1-SNAPSHOT.jar /home/myapp/log6144.jar
# 启动网关服务
ENTRYPOINT ["java","-jar","log6144.jar"]

