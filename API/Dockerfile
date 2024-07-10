# Sử dụng hình ảnh OpenJDK làm cơ sở
FROM openjdk:21

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép file .jar của ứng dụng vào thư mục làm việc trong container
COPY target/ProjectBanHang-0.0.1-SNAPSHOT.jar  . 
# Mở cổng mạng 8080 (hoặc cổng mà ứng dụng Spring Boot của bạn sử dụng)
EXPOSE 8080

# Khởi chạy ứng dụng khi container được khởi động
CMD ["java", "-jar", "ProjectBanHang-0.0.1-SNAPSHOT.jar"]
