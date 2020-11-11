Credit Suisse Trial
===============================
###1. Technologies used
* Maven 3.0
* Spring 5.2.8.RELEASE
* H2 1.4.187
* JSF 2.2.12
* Primefaces 5.2

###2. To Run the tests
```shell
$ mvn -U test
```
###3. To Run this project locally
```shell
$ mvn jetty:run
```
Access ```http://localhost:8080/spring-primefaces/main.xhtml```

or run the linux script

```shell
$ ./run-app.sh
```
###4. To import this project into Eclipse IDE
1. ```$ mvn eclipse:eclipse```
2. Import into Eclipse via **existing projects into workspace** option.
3. Done.
