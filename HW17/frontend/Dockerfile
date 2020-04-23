FROM node AS compiler

WORKDIR /opt

COPY ./package* ./
RUN npm ci

COPY .flowconfig .
COPY ./src ./src
RUN npm run flow

COPY ./public ./public
COPY ./jsconfig.json .
RUN npm run build

FROM nginx

COPY ./nginx.conf  /etc/nginx/conf.d/default.conf
COPY --from=compiler /opt/build /usr/share/nginx/html
