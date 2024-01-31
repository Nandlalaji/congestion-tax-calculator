# Congestion Tax Calculator Solution Note

### Solution is Spring boot 3 application.

### SOLID principle and Chain of responsibility Design patter is used

### I implemented DDD - Domain Driven Design to make security as different domain

### I also externalise the configurations for easy maintainability of application

### To get bonus point i also implemented for other city and also spring security to use jwt token

### Project Specs

* #### Java 17
* #### Maven
* #### Junit
* #### Springboot 3
* #### Spring security
* #### SpringbootTest for integration test

#### To run application

mvn spring-boot:run

#### Note* - You need to generate jwt token before using main api

**URL:** localhost:8080/v1/auth/getToken

**Post Request:**
Below-mentioned request is add for the sample. You can add your email and password in
app-user-config.yml

````
{
    "email":"test1@gmail.com",
    "password":"password@123"
}
````

**Success Response:**

````
{
"token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MUBnbWFpbC5jb20iLCJpYXQiOjE3MDY3MTM3MDEsImV4cCI6MTcwNjcxNTE0MX0.mVvlU7pCwc_2czmzsErZw1Z8bHZjRbCIL1SUYPRU5Yc"
}
````

**URL:** http://localhost:8080/v1/congestion-tax/calculate-tax

**Header with generated token:**
Generated token should be used in request.
In postman, you will find Authorization tab beneath url section.
Authorization option should be after Params tab and before Headers tab. You need to select bearer token option and paste generated token in Token box.

**Post Request:**
Below-mentioned request is add for the sample and will be changed as per requirement.

````
{
    "city": "gothenburg",
    "vehicle": "car",
    "taxDateTime": [
        "2013-02-07 06:23:27",
        "2013-02-07 06:35:27",
        "2013-02-07 06:50:27",
        "2013-02-07 07:10:27",
        "2013-02-07 07:23:00",
        "2013-02-08 07:10:27"
    ]
}
````

**Failure Response:**

````
Date time in parameter is null
````

"message" text will vary depends on the request body

**Success Response:**

````
{
    "fee": 36
}
````

### Testcase

* Junit is being used for Service level testing.
* Integration test is on controller level- @Profile is used to allow api endpoint to pass security without token in integration test.

### What can be more optimized!

* Using H2 DB OR config server for better handling of application configurations
* For logging we can add Slf4j
* API response that can contains date wise total fee. Didn't add as it was not requirement for now
* More test cases around security part

