FROM node:19.9.0-alpine as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build


FROM nginx:alpine

COPY nginx.conf /etc/nginx/nginx.conf

COPY --from=build-stage /app/dist/frontend/browser /usr/share/nginx/html

EXPOSE 80

