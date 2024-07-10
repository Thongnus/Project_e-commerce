# Sử dụng một image nginx làm base image
FROM nginx:alpine



# Copy tất cả các tệp tĩnh từ thư mục dự án vào thư mục `/usr/share/nginx/html` trong container
COPY . /usr/share/nginx/html/


# EXPOSE cổng 80 để có thể truy cập vào web server nginx từ bên ngoài
EXPOSE 80

# CMD khởi chạy web server nginx khi container được khởi động
CMD ["nginx", "-g", "daemon off;"]
