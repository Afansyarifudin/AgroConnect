# AgroConnect ML API
AgroConnect-Bangkit Capstone Project C23-PS127

# How to Setup
1. `git pull`
2. `git checkout ml-api`
3. `docker build -t agroconnect-ml-api .`
4. `docker run --rm -p 5000:5000 agroconnect-ml-api` make sure you have docker installed on you local --rm to delete the container when it didin't use 
5. `docker container ls -la` to see all container that's running on you local 
6. `docker container stop <container-id>` to stop you running container 


## API Documentation 

    Products Routes :
	    GET("/products")   

    Farmers Routes :
	    GET("/farmer")             



