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

## Example of the URL for getting the score
    http://localhost:5000/getScore?supplier_data=Andi&supplier_data=-7&supplier_data=106&supplier_data=0&supplier_data=0&supplier_data=2000&supplier_data=200&supplier_data=0&supplier_data=0

## Example output
    [{"Name":"takamanu","Lat":37.4220936,"Long":-122.083922,"Commodity_1":90,"Commodity_2":0,"Commodity_3":4342,"Commodity_4":403,"Commodity_5":0,"Commodity_6":0,"Score":0.2356440964,"Location_rank":2},{"Name":"admin","Lat":-6.9175,"Long":107.6191,"Commodity_1":200,"Commodity_2":80,"Commodity_3":20,"Commodity_4":15,"Commodity_5":100,"Commodity_6":300,"Score":0.0831030247,"Location_rank":1},{"Name":"takamanu","Lat":-6.137637,"Long":106.944656,"Commodity_1":101,"Commodity_2":0,"Commodity_3":101,"Commodity_4":100,"Commodity_5":0,"Commodity_6":0,"Score":0.0767311531,"Location_rank":1},{"Name":"takamanu","Lat":-7.056752,"Long":110.418433,"Commodity_1":360,"Commodity_2":161,"Commodity_3":20,"Commodity_4":0,"Commodity_5":20,"Commodity_6":0,"Score":0.0760018425,"Location_rank":1},{"Name":"takamanu","Lat":-6.906497,"Long":107.644691,"Commodity_1":0,"Commodity_2":0,"Commodity_3":0,"Commodity_4":10,"Commodity_5":0,"Commodity_6":0,"Score":0.0072931061,"Location_rank":1}]

    - The higher the Score, the better
    - The lower the Location_rank, the better