FROM python:3.10

# COPY requirements.txt ./requirements.txt
# COPY main.py ./main.py

# RUN pip install -r requirements.txt

# CMD [ "python", "./main.py" ]

WORKDIR /app
COPY requirements.txt .
RUN python -m venv /venv
ENV PATH="/venv/bin:$PATH"
RUN pip install --upgrade pip
RUN pip install -r requirements.txt
COPY main.py .

CMD [ "python", "./main.py" ]