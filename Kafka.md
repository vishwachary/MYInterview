Running Apache Kafka locally with Rancher Desktop’s Kubernetes is a common developer need, 
https://medium.com/@anilkumar.kanasani/installing-kafka-on-windows-71d7d24763ad


.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties 

.\bin\windows\kafka-server-start.bat .\config\server.properties


CREATE  A TOPIC
.\bin\windows\kafka-topics.bat --create --topic test-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

LIST TOPICS

.\bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092


START PRODUCER
.\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test-topic

Start a consumer in another terminal:
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test-topic --from-beginning

