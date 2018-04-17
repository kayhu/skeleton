docker run -t -d --name redis -p 6379:6379 -v D:/code/docker/volume/redis:/data redis:3.0.7 redis-server /data/redis.conf --requirepass P@ssw0rd

docker run -t -d --name zookeeper -p 2181:2181 -v D:/code/docker/volume/zookeeper/conf:/conf -v D:/code/docker/volume/zookeeper/data:/data --restart always zookeeper:3.4.10

docker run -t -d --name mysql -p 3306:3306 -v D:/code/docker/volume/mysql/conf.d:/etc/mysql/conf.d -v D:/code/docker/volume/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=P@ssw0rd mysql:5.7.12

docker run -t -d --name nginx -p 80:80 -p 443:443 -v D:/code/docker/volume/nginx/conf.d:/etc/nginx/conf.d -v D:/code/docker/volume/nginx/cert:/etc/nginx/cert nginx:1.8.1

docker run -t -d --name mongo -p 27017:27017 -v D:/code/docker/volume/mongo/data:/data mongo:3.6.3

docker run -t -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.6.9-management

docker run -t -d --name jenkins -p 8000:8080 -p 50000:50000 -v D:/code/docker/volume/jenkins:/var/jenkins_home jenkins/jenkins:2.107.1

docker run -t -d --name nexus -p 8081:8081 -v D:/code/docker/volume/nexus:/nexus-data sonatype/nexus3:3.10.0

docker run -t -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube:6.7.3