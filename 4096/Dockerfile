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
COPY target/gateway4096-0.0.1-SNAPSHOT.jar /home/myapp/gateway4096.jar
# 修改这个文件的访问时间和修改时间为当前时间
RUN bash -c "touch /gateway4096.jar"
# 暴漏端口
EXPOSE 4096
# 启动服务
ENTRYPOINT ["java","-jar","gateway4096.jar"]
