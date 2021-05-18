# Jcart (forked from [sivaprasadreddy](https://github.com/sivaprasadreddy/jcart))
JCart is a simple e-commerce application built with Spring.

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c9cd10aebb104847aeb0240d73d64f30)](https://www.codacy.com/app/rajadileepkolli/jcart?utm_source=github.com&utm_medium=referral&utm_content=rajadileepkolli/jcart&utm_campaign=badger) 
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=rajadileepkolli_jcart&metric=alert_status)](https://sonarcloud.io/dashboard?id=rajadileepkolli_jcart)
[![SonarCloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=rajadileepkolli_jcart&metric=coverage)](https://sonarcloud.io/component_measures/metric/coverage/list?id=rajadileepkolli_jcart)
[![SonarCloud Bugs](https://sonarcloud.io/api/project_badges/measure?project=rajadileepkolli_jcart&metric=bugs)](https://sonarcloud.io/component_measures/metric/reliability_rating/list?id=rajadileepkolli_jcart)
[![SonarCloud Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=rajadileepkolli_jcart&metric=vulnerabilities)](https://sonarcloud.io/component_measures/metric/security_rating/list?id=rajadileepkolli_jcart)

#### This project is analysed on [![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-black.svg)](https://sonarcloud.io/dashboard?id=rajadileepkolli_jcart)

## How to use jcart-admin application
login to Admin by using the url http://localhost:9090 and by entering the credentials as below, to use your own credentials edit data.sql present under jcart-core module resources folder

	Username :: rajadileepkolli@gmail.com
	Password :: superadmin
		
  
===============
How to run it?

git clone https://github.com/sivaprasadreddy/jcart.git

cd jcart

jcart> mvn clean install

jcart> cd jcart-admin

jcart-admin> mvn spring-boot:run

Now you can access JCart Admin at https://localhost:9443/ 


jcart> cd jcart-site

jcart-site> mvn spring-boot:run

Now you can access JCart Site at https://localhost:8443/ 

