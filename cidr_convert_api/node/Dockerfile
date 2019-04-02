FROM node:9-alpine

WORKDIR /app

COPY package.json .

RUN while true; do npm install && break; done

COPY . .

CMD ["npm", "start"]
# docker build -t devops-api-node .
# docker run -ti -p 8000:8000 devops-api-node
