# AgroConnect
AgroConnect-Bangkit Capstone Project C23-PS127

## Before Develop
- Run `git pull` to update you local with remote in github
- Checkout to Branch api-dev `git checkout api-dev` 
- Run `npm install` for first initialize project after clone repo

## Run Localy 
- Make sure you have PostgresSQL
- Configure on `config/config.json`. Please configure like you Postgres in your local
- Cd `src` then
- Run ```npx sequelize-cli db:create```
- Run ```npx sequelize-cli db:migrate```

## Description 
This is a project of NodeJS using ExpressJS framework and detailed tech stack below 
| Tech          | Used as       |
| ------------- | ------------- |
| ExpressJS    | Framework     |
| PostgreSQL         | Database     |
| Postman       | API Test Endpoint |

## API Specification

http://localhost:3000

    Users Routes :
	    POST("/users/register")
		POST("/users/login")

    Categories Routes :
	    GET("/categories")              
		GET("/categories/:categoryId")     
		POST("/categories")
		PUT("/categories/:categoryId")     
		DELETE("/categories/:categoryId")  

    Products Routes :
	    GET("/products")              
		GET("/products/:productId")     
		POST("/products")
		PUT("/products/:productId")     
		DELETE("/products/:productId")  

    Demands Routes :
	    GET("/demands")              
		GET("/demands/:demandId")     
		POST("/demands")
		PUT("/demands/:demandId")     
		DELETE("/demands/:demandId")  

