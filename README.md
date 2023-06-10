<div align="center">

<!--lint ignore no-dead-urls-->

# AgroConnect-Machine Learning-API
![GitHub Workflow Status (with branch)](https://img.shields.io/github/actions/workflow/status/Afansyarifudin/AgroConnect/deploy.yml)
![GitHub watchers](https://img.shields.io/github/watchers/Afansyarifudin/AgroConnect?style=social)![GitHub last commit (branch)](https://img.shields.io/github/last-commit/Afansyarifudin/AgroConnect/api-dev)

Supply chain app of farmer. The game-changer that brings farmers, suppliers, and customers together!

<img width="122" src="https://github.com/Afansyarifudin/AgroConnect/assets/68774609/1de150f1-d48c-4101-9df1-e15ffc7803cb" alt="AgroConnect">  

</div>

## Installation
clone 
```bash
git clone --branch ml-api https://github.com/Afansyarifudin/AgroConnect
``` 

## Usage
### Before Develop
Update you local repo with remote using
```bash
git pull 
``` 
Checkout to branch `ml-api` if you're already skip this 
```bash
git checkout ml-api
``` 

### Run in your local using docker 
> **Note**
> Make sure you have installed docker or docker desktop 
>
Build the image using this command 
```bash
docker build -t agroconnect-ml-api .
``` 
> **Note**
> If you are getting error that cannt find dockerfile or no such file to build 
> change directory into AgroConnect and make sure you are in the `ml-api` branch 
Run docker image that has been builded before
```bash
docker run --rm -p 5000:5000 agroconnect-ml-api
``` 
Run this command to list all container that's running in your local 
```bash
docker container ls -la
``` 
to stop container that's running using 
```bash
docker container stop <REPLACE WITH YOU CONTAINER ID>
```
or just press `Ctrl + C`

### Run in your local without docker 
To run without docker just run using this command 
```bash
pip install -r requirements.txt
```
> **Note**
> Make sure you have minimum python 3.10.0 install 
>
To run the web server using this 
```bash
python main.py
```

## Description 
This is a project of Python and Flask framework and detailed tech stack below 
| Tech          | Used as       |
| ------------- | ------------- |
| Flask    | Framework     |
| PostgreSQL         | Database     |
| Postman       | API test endpoint |
| Docker       | Build image | 
| Tensorflow  | Machine Learning | 

## API Documentation 

http://localhost:5000
>
[Api Documentation](https://documenter.getpostman.com/view/24922206/2s93sc4Xex)

    Products Routes :
	    GET("/products")   
	    GET("/farmer") 
	    GET("/getScore?")

## Example of the URL for getting the score
    http://localhost:5000/getScore?supplier_data=Andi&supplier_data=-7&supplier_data=106&supplier_data=0&supplier_data=0&supplier_data=2000&supplier_data=200&supplier_data=0&supplier_data=0

## Example output
    [{"Name":"takamanu","Lat":37.4220936,"Long":-122.083922,"Commodity_1":90,"Commodity_2":0,"Commodity_3":4342,"Commodity_4":403,"Commodity_5":0,"Commodity_6":0,"Score":0.2356440964,"Location_rank":2},{"Name":"admin","Lat":-6.9175,"Long":107.6191,"Commodity_1":200,"Commodity_2":80,"Commodity_3":20,"Commodity_4":15,"Commodity_5":100,"Commodity_6":300,"Score":0.0831030247,"Location_rank":1},{"Name":"takamanu","Lat":-6.137637,"Long":106.944656,"Commodity_1":101,"Commodity_2":0,"Commodity_3":101,"Commodity_4":100,"Commodity_5":0,"Commodity_6":0,"Score":0.0767311531,"Location_rank":1},{"Name":"takamanu","Lat":-7.056752,"Long":110.418433,"Commodity_1":360,"Commodity_2":161,"Commodity_3":20,"Commodity_4":0,"Commodity_5":20,"Commodity_6":0,"Score":0.0760018425,"Location_rank":1},{"Name":"takamanu","Lat":-6.906497,"Long":107.644691,"Commodity_1":0,"Commodity_2":0,"Commodity_3":0,"Commodity_4":10,"Commodity_5":0,"Commodity_6":0,"Score":0.0072931061,"Location_rank":1}]

    - The higher the Score, the better
    - The lower the Location_rank, the better

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

## Contributor 

Developed by C23-PS127 Bangkit Capstone Team

## License

[MIT](https://choosealicense.com/licenses/mit/)
