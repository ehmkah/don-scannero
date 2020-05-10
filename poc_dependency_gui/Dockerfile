FROM node:10
WORKDIR /usr/src/app
COPY package*.json ./
RUN yarn install
COPY . .
RUN yarn build

FROM nginx:1.18
WORKDIR /public
COPY --from=0 /usr/src/app/build /usr/share/nginx/html/
EXPOSE 80