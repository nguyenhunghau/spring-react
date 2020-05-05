FROM alpine
EXPOSE 80
ADD config/default.conf /etc/nginx/conf.d/default.conf
COPY . /react
RUN apk add nginx && \
    mkdir /run/nginx && \
    apk add nodejs && \
    apk add npm && \
    cd /react && \
    npm install && \
    npm run build && \
    apk del nodejs && \
    apk del npm && \
    mv /react/build /home && \
    cd /react && \
    rm -rf * && \
    mv /home/build /react;
CMD ["/bin/sh", "-c", "exec nginx -g 'daemon off;';"]
WORKDIR /react