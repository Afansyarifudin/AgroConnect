<div align="center">

<!--lint ignore no-dead-urls-->

# AgroConnect 

Supply chain app of farmer. The game-changer that brings farmers, suppliers, and customers together!

<img width="122" src="https://github.com/Afansyarifudin/AgroConnect/assets/68774609/1de150f1-d48c-4101-9df1-e15ffc7803cb" alt="AgroConnect">  

</div>


## Before Develop
- Run `git pull` to update you local with remote in github
- Checkout to Branch api-dev `git checkout api-dev` 
- Run `npm install` for first initialize project after clone repo

## Run Localy 
- Make sure you have PostgresSQL
- Copy `.env.example` to .`.env` and Fill the value
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

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

## Contributor 

Developed by C23-PS127 Bangkit Capstone Team

## License

[MIT](https://choosealicense.com/licenses/mit/)

