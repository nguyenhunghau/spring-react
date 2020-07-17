FROM alpine
EXPOSE 80
ADD config/default.conf /etc/nginx/conf.d/default.conf --no-cache
COPY . /react
RUN apk add nginx && \
    mkdir /run/nginx;
CMD ["/bin/sh", "-c", "exec nginx -g 'daemon off;';"]
WORKDIR /react