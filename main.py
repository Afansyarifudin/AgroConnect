from flask import Flask, jsonify, request
import json
from dotenv import load_dotenv
import psycopg2
load_dotenv()

app=Flask(__name__)

#Database connection 
# conn = psycopg2.connect(database=os.getenv("DB_DATABASE"), 
#                         user=os.getenv("DB_USERNAME"),
#                         password=os.getenv("DB_PASSWORD"), 
#                         host=os.getenv("DB_HOST"), 
#                         port="5432")

# conn = psycopg2.connect(database="agroconnect_db_prod", user="postgres", password="agroconnect", host="34.128.87.179", port="5432")

# query for database
# cur.execute()

# commit the changes
# conn.commit()

# close the cursor and connection
# cur.close()
# conn.close()



@app.route("/")
def index():
    return "Welcome to AgroConnect ML API"

@app.route("/products")
def get_all_products():
    try:
        #connection to db
        conn = psycopg2.connect(database="agroconnect_db_prod", 
                                user="postgres", 
                                password="agroconnect", 
                                host="34.128.87.179", 
                                port="5432")

        #create a cursor 
        cur = conn.cursor()

        # Qeury Selcet all from table public.products 
        cur.execute('''SELECT id, "name", amount, "location", crop_date, estimate_exp, category_id, user_id, "createdAt", "updatedAt"
                        FROM public."Products"''')

        # Fetch all data 
        data = cur.fetchall()

        # close sursor and connection 
        cur.close()
        conn.close()

        return data

    except psycopg2.Error as e:
        print("Connection failed to database: ", e)
        return

@app.route("/farmer")
def get_data_farmer():
    try:
        conn = psycopg2.connect(database="agroconnect_db_prod", 
                                user="postgres", 
                                password="agroconnect", 
                                host="34.128.87.179", 
                                port="5432")

        #create a cursor 
        cur = conn.cursor()

        #  Query SQL to Join Data from table Users, Category, and Products
        query = """
        SELECT u.username AS "Name",
            SPLIT_PART(p.location, ',', 1) AS "Lat",
            SPLIT_PART(p.location, ',', 2) AS "Long",
            SUM(CASE WHEN c.id = 7 THEN p.amount ELSE 0 END) AS "Commodity_1",
            SUM(CASE WHEN c.id = 8 THEN p.amount ELSE 0 END) AS "Commodity_2",
            SUM(CASE WHEN c.id = 9 THEN p.amount ELSE 0 END) AS "Commodity_3",
            SUM(CASE WHEN c.id = 10 THEN p.amount ELSE 0 END) AS "Commodity_4",
            SUM(CASE WHEN c.id = 5 THEN p.amount ELSE 0 END) AS "Commodity_5",
            SUM(CASE WHEN c.id = 6 THEN p.amount ELSE 0 END) AS "Commodity_6"
        FROM public."Products" p
        JOIN public."Users" u ON u.id = p.user_id
        JOIN public."Categories" c ON c.id = p.category_id
        GROUP BY u.username, p.location
        """

        # Execute Qeury Selcet all from table public.products 
        cur.execute(query)

        # Fetch all data 
        rows = cur.fetchall()

        # Coloumn names 
        column_names = ["Name", "Lat", "Long", "Commodity_1", "Commodity_2", "Commodity_3", "Commodity_4", "Commodity_5", "Commodity_6", "Date_Posted"]

        # Respon Table 

        # # Create a string to hold the table data
        # table_str = ""

        # # Append column names to the table string
        # table_str += "\t".join(column_names) + "\n"

        # # Append each data row to the table string
        # for row in rows:
        #     formatted_row = "\t".join(str(value) for value in row)
        #     table_str += formatted_row + "\n"

        # Respons JSON 

        data = []

        # Iterate over the rows and create dictionaries for each row
        for row in rows:
            data_row = {column_names[i]: str(value) for i, value in enumerate(row)}
            data.append(data_row)

        # Convert the data to JSON
        json_data = json.dumps(data)

        # Close the cursor and connection
        cur.close()
        conn.close()

        # return table_str
        return json_data

    except Exception as e: 
        print("Connection failed to database:", e)
        return str(e)

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)