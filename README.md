ApiQuota project

This is an API Quota small service to handle quota consumption from users

for this challenge there is a default amount of 5 that can be handled through a table in the DB
and can be updated with events or whatever method suits the best


in order to accomplish the assesment 
I implemented a strategy pattern to solve the daytime usage of mysql or elastic depending on time window

for the consumption of quota, in order to make it scalable and also be used for other services. I implemented a kafka topic with the userId
this allows us usage in an event driven ecosystem with distributed systems.
also makes it asyncronus processing of the quota.

My suggestion In order to keep both databases syncronized, I'd use kafka connectors 2 source connectors and 2 sink connectors
so adding the proper configurations 
- source connector will get the data from every change in Mysql and send it to e.g. MYSQL_SOURCE_TOPIC, and the sinc connector will subscribe to the sink connector topic and store the data into elastic
- source connector will listen to every change in elastic and send it to e.g. ELASTIC_SOURCE_TOPIC, and the sink would do the same as above to store changes in mysql.

  in order to run this project

  Clone the repo
  go to devploy folder and run:

$ docker-compose up -d 

here is the postman collection to test it
https://api.postman.com/collections/4469107-63a9362d-89bf-4f86-a7be-a1de4808e5de?access_key=PMAT-01HS7498E3TB480Y0NVDFC08K1
