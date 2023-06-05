FROM node:16.18-alpine

WORKDIR /usr/src/app

COPY package*.json ./

RUN npm install

COPY . .

RUN export NODE_ENV=development

EXPOSE 3000

CMD [ "node", "server.js" ]

