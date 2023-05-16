# AgroConnect
AgroConnect-Bangkit Capstone Project C23-PS127

## Before Develop
- Run `git pull` to update you local with remote in github
- Checkout to Branch api-dev `git checkout api-dev` 
- Run `npm install` for first initialize project after clone repo

## Description 
This is a project of RESTFull API using Go language and detailed tech stack below 
| Tech          | Used as       |
| ------------- | ------------- |
| ExpressJs     | Framework     |
| MySQL         | Dataabase     |
| Postman       | API Test Endpoint |

## API Specification

http://localhost:3000

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

	