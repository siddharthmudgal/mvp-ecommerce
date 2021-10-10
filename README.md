# MVP E-commerce



## About

Ecommerce challenge.



## Pre-requisites

Docker desktop

## Diagrams

![architecture](./architecture.jpeg)

![deployment](./deployment.jpeg)

## DB Schema
![schema](./db-schema.png)

## How to run

#### start containers

```bash
cd mvp-ecommerce
docker-compose -f docker-compose.yml up
```

#### stop containers

```bash
cd mvp-ecommerce
docker-compose -f docker-compose.yml down
```

#### Static website included
```
cd mvp-ecommerce/static-web-page
```
open index.html in Chrome/firefox

#### Rest API's exposed

Please note entire rest collection is available under (importable via insomnia) : 
```
cd mvp-ecommerce/rest-samples
```

1. users -> GET / POST
    ```
   {
   	"username": "user3",
   	"userType": "CUSTOMER"
   }
   ```
2. products -> GET / POST
    ```
   {
   	"name": "APPLE",
   	"description": "lemon",
   	"price": 132,
   	"quantity": 12
   }
   ```
3. users/cart -> GET
4. users/{user_id}/addtocart/{product_id}
5. users/{user_id}/removefromcart/{product_id}

#### UX worflow

If you open the portal without setting up the database, the users screen will be empty
![](./assets/images/screen1.png)

Fire up this rest API to load a user
![](./assets/images/screen2.png)

Success response
![](./assets/images/screen3.png)

Now the screen will display the user in a dropdown
![](./assets/images/screen4.png)

However, the products will remain empty as they need to be added to database as well
![](./assets/images/screen5.png)

Fire up rest API
![](./assets/images/screen6.png)

Success Response
![](./assets/images/screen7.png)

Now your marketplace db is setup
![](./assets/images/screen8.png)


## Code coverage report

| com.dealstop | 93% (15/16) |
| ------------- | ----------- |


## Bonus
1. Github CI-CD
2. Docker build
3. Search enabled in products / cart