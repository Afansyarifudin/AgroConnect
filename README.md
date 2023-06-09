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
### Before Develop
Update you local repo with remote using
```bash
git pull 
``` 
Checkout to branch `api-dev` if you're already skip this 
```bash
git checkout api-dev
``` 
Run this command to install node modules 
```bash
npm install
``` 

### Run in your local 
> **Note**
> Make sure you have installed postgreSQL
>
Copy `.env.example` and rename to be .env then copy inside `.env.example` paste all the value into `.env` using 
```bash
cp .env.example .env
``` 
Change directory to src using 
```bash
cd src
``` 
Run this command to create database 
```bash
npx sequelize-cli db:create
``` 
> **Warning**
> If create db is not successfully. check database is already use or not or may check your `.env` configuration 
>
Run this to create migration 
```bash
npx sequelize-cli db:migrate
```
Run this to for database seeder (optional)
```bash
npx sequelize-cli db:seed:all
```

## Description 
This is a project of NodeJS using ExpressJS framework and detailed tech stack below 
| Tech          | Used as       |
| ------------- | ------------- |
| ExpressJS    | Framework     |
| PostgreSQL         | Database     |
| Postman       | API test endpoint |
| Sequalize    | ORM database  | 
| Docker       | Build image | 


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

