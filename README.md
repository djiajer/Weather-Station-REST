# Spring Course. Practical Project 3
Final study project at the Spring course (https://www.udemy.com/course/spring-alishev/).
The project was created with Spring Boot and Hibernate Validator. For collaboration with PostgreSQL database, it's using Hibernate + Spring JPA.

REST application which receives measurement information from abstract sensors (temperature rainy flag, sensor name) and stores to the database.
Also, it provides the possibility to get a list of all measurements in JSON format, register new sensors, get the count of rainy days from the database.

The application provides next endpoints:
- ```/sensors/registration``` - endpoint for sensor registration (allow POST request)
- ```/measurements/add``` - endpoint for measurement add (allow POST requests)
- ```/measurements``` - returns a list of all measurements from the database in JSON format (allow GET request)
- ```/measurements/rainyDaysCount``` - returns count of rainy days from database

Example of requests to ```/sensors/registration``` endpoint:
```
{
    "name":"Test Sensor"
}
```
Example of requests to ```/measurements/add``` endpoint:
```
{
    "value":11.30,
    "raining":true,
    "sensor":{
        "name":"Belarus"
    }
}
```

Example of response for GET requests to ```/measurements``` endpoint:
```
[
    {
        "value": 23.20,
        "raining": true,
        "sensor": {
            "name": "Canada"
        }
    },
    {
        "value": 22.29,
        "raining": false,
        "sensor": {
            "name": "Canada"
        }
    },
    {
        "value": 15.82,
        "raining": true,
        "sensor": {
            "name": "Ukraine"
        }
    }
]
```
Example of response for GET requests to ```/measurements/rainyDaysCount``` endpoint:
```
{
    "rainyDaysCount": 501
}
```


