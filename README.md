<div align="center">

<!--lint ignore no-dead-urls-->

# AgroConnect-API
![GitHub Workflow Status (with branch)](https://img.shields.io/github/actions/workflow/status/Afansyarifudin/AgroConnect/deploy.yml)
![GitHub watchers](https://img.shields.io/github/watchers/Afansyarifudin/AgroConnect?style=social)
![npm](https://img.shields.io/npm/v/npm)![GitHub last commit (branch)](https://img.shields.io/github/last-commit/Afansyarifudin/AgroConnect/api-dev)

Supply chain app of farmer. The game-changer that brings farmers, suppliers, and customers together!

<img width="122" src="https://github.com/Afansyarifudin/AgroConnect/assets/68774609/1de150f1-d48c-4101-9df1-e15ffc7803cb" alt="AgroConnect">  

</div>

## Installation
> **Warning**
> This branch is used only for production. For development please use branch [api-dev](https://github.com/Afansyarifudin/AgroConnect/tree/api-dev)

## Usage
> **Warning**
> This branch is used only for production. For development please use branch [api-dev](https://github.com/Afansyarifudin/AgroConnect/tree/api-dev)

## Description 
This is a project of NodeJS using ExpressJS framework and detailed tech stack below 
| Tech          | Used as       |
| ------------- | ------------- |
| ExpressJS    | Framework     |
| PostgreSQL         | Database     |
| Postman       | API test endpoint |
| Sequalize    | ORM database  | 
| Docker       | Build image | 
| Cloud Run    | Serverless deployment |
| Artifacts Registry | Store image |
| Github Action | Pipeline for Deployment |


## API Specification

[Postman Documentation](https://documenter.getpostman.com/view/24922206/2s93m8zLPs)

    Users Routes :
	    	POST("/users/register")
		POST("/users/login")

    Categories Routes :
	    	GET("/categories") 
        	GET("/categories/search")     
		GET("/categories/:categoryId")     
		POST("/categories")
		PUT("/categories/:categoryId")     
		DELETE("/categories/:categoryId")  

    Products Routes :
	    	GET("/products")
        	GET("/products/search")               
		GET("/products/:productId")     
		POST("/products")
		PUT("/products/:productId")     
		DELETE("/products/:productId")  

    Demands Routes :
	    	GET("/demands")
        	GET("/demands/search")       
		GET("/demands/:demandId")     
		POST("/demands")
		PUT("/demands/:demandId")     
		DELETE("/demands/:demandId")  

## Contributing

Please make pull request on branch [api-dev](https://github.com/Afansyarifudin/AgroConnect/tree/api-dev)

## Contributor 

Developed by C23-PS127 Bangkit Capstone Team

## License

[MIT](https://choosealicense.com/licenses/mit/)
