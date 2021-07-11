# myshop

Tech Stack: Spring Boot | Redis | RocketMQ | Sentinel | Mysql    

An online shopping system designed to support heavy traffic

* RocketMQ for ingress traffic, which improves system throughput and ensures system stability

* Redis as cache middleware to reduce the frequency of database access

* Message queue to process tasks asynchronously and improve overall system performance

* Leverages a delayed message queue to control the closing of timeout orders and optimize system resources

* Sentinel for limiting traffic, such as crawlers and malicious requests
