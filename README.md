# Overview

This is a sample REST API for managing contacts using Spring Boot.

## Usage
* Build the Spring Boot Application using 'mvn install'
* Start the application using 'mvn spring-boot:run'
* The application should be running at 'http://localhost:8080'
* Once the application is running, the table below describes the relative endpoints used to manage contacts with the
client of your choice ( cURL, Postman,etc.)


| HTTP Method     | Route               | Description                                                                                                 |
|-----------------|---------------------|-------------------------------------------------------------------------------------------------------------|
| GET             | /contacts           | List all contacts                                                                                           |
| POST            | /contacts           | Create a new contact                                                                                        |
| PUT             | /contacts/{id}      | Update a contact                                                                                            |
| GET             | /contacts/{id}      | Get a single contact                                                                                        |
| DELETE          | /contacts/{id}      | Delete a single contact                                                                                     |
| GET             | /contacts/call-list | Returns a call list, which contains contacts that have a home phone sorted by last name, then by first name |
|                 |                     | by last name, then by first name                                                                            |


Sample Input:
```
{
address": {
    "street": "1234 Main",
    "city": "Columbus",
    "state":"OH",
    "zip": "43221"
},
"email": "john@doe.com",
"name" : {
    "first":"John",
    "middle": "A",
    "last": "Doe"
},
    "phone": [{
        "number": "1234",
        "type": "mobile"
    },
    {
        "number": "12345",
        "type": "home"
}]
}
```