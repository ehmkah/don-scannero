FROM node:10
WORKDIR /usr/src/app
COPY package*.json ./
RUN yarn install
COPY . .
RUN yarn build

FROM nginx:1.18
COPY --from=0 /usr/src/app/build /usr/share/nginx/html/
COPY nginx.conf /etc/nginx/nginx.conf
RUN mkdir /usr/share/nginx/html/data

EXPOSE 80