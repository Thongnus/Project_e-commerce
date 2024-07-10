const jwtToken = localStorage.getItem('jwtToken');
    const headers = {
        'Authorization': `Bearer ${jwtToken}`
    };
function getMunberCart() {
    fetch('http://localhost:8085/api-carts/getCart', {
        method: 'GET',
        headers: headers
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Failed to fetch cart data');
        }
    })
    .then(function(Response){
      document.querySelector("header .content .funtion .funtion-list .cart span.cart-number").innerHTML=Response.length
  })

    .catch(error => {
        console.error('Error:', error);
    });
}
getMunberCart()
let inputUserName = document.querySelector(".container .container-content input[id=username]")
let email = document.querySelector(".container .container-content input[id=fullname]")
let inputAddress = document.querySelector(".container .container-content input[id=address]")
let inputPhoneNumber = document.querySelector(".container .container-content input[id=phonenumber]")
let btnUpdate = document.querySelector(".container .container-content button")

function checkdata(data){
    console.log(data.id)
    // if(inputFullName.value && inputFullName.value && inputAddress.value && inputPhoneNumber.value){
    //     btnUpdate.classList.add("enable")
    // }
    // else{
        
        btnUpdate.onclick = ()=>{
            const jwtToken = localStorage.getItem('jwtToken');
            const headers = {
                'Authorization': `Bearer ${jwtToken}`,
                'Content-Type': 'application/json'
            };
            let data_send ={

                email: email.value,
                address : inputAddress.value,
                numberphone : inputPhoneNumber.value
            }
            console.log(data.id)
            fetch(`http://localhost:8085/admin/users/update/${data.id}`, {
                method: 'PUT',
                headers: headers,
                body: JSON.stringify(data_send)
            })
            .then(response => {
                if (response.ok) {
                    alert("Thông tin đc sửa thành công");
                    fetchDataAndDisplay();
                } else {
                    alert("Cập nhật không thành công.");
                }
            })
            .catch(error => console.error('Lỗi:', error));
            

        }
    }
// }

fetchDataAndDisplay()
//update password
const token = localStorage.getItem('jwtToken');

                 if(!token){
             window.location.href='../../web api nam 3 ki2/view/login.html'
               }
                    // Lấy các phần tử input
              function updatepass(){
            const currentPasswordInput = document.getElementById('currentPassword');
            const newPasswordInput = document.getElementById('newPassword');
            const confirmNewPasswordInput = document.getElementById('confirmNewPassword');

            // Lấy giá trị từ các input
            const currentPassword = currentPasswordInput.value;
            const newPassword = newPasswordInput.value;
            const confirmNewPassword = confirmNewPasswordInput.value;

            // Kiểm tra xác nhận mật khẩu mới
            if (newPassword !== confirmNewPassword) {
                alert("Mật khẩu mới và xác nhận mật khẩu mới không khớp!");
                return;
            }

            // Tạo payload dữ liệu để gửi đến máy chủ
            const data = {
                currentPassword: currentPassword,
                newPassword: newPassword
            };

            // Gửi yêu cầu đổi mật khẩu đến máy chủ
            fetch('http://localhost:8085/admin/users/updatepass', {
                method: 'Put',
                headers: {
                    'Authorization': 'Bearer ' + token,
                    'Content-Type': 'application/json'
                    // Thêm các header cần thiết như Authorization nếu cần
                },
                body: JSON.stringify(data)
            })
            .then(response => {
              
                if (!response.ok) {
                    throw new Error('Có lỗi xảy ra khi thực hiện yêu cầu đổi mật khẩu.');
                }

                // Xử lý kết quả trả về từ máy chủ nếu cần
                return response.json();
            })
            .then(data => {
                console.log(data);
                // Xử lý dữ liệu trả về từ máy chủ nếu cần
                alert('Đổi mật khẩu thành công!');
                window.location.reload();
            })
            
            .catch(error => {
                // Xử lý lỗi nếu có
                console.error('Đã xảy ra lỗi:', error);
                alert('Sai mật khẩu.');
            });}





// OrderUser
        // Hàm để lấy dữ liệu từ API và hiển thị trên trang HTML
        function fetchDataAndDisplay() {
            const jwtToken = localStorage.getItem('jwtToken');
            const headers = {
                'Authorization': `Bearer ${jwtToken}`,
                'Content-Type': 'application/json'
            };
            
            let stt=0;
            fetch('http://localhost:8085/api-orders/user', {
                method: 'GET',
                headers: headers
            })
                .then(response => response.json())
                .then(data => {
                    console.log(data)
                    // Xóa dữ liệu cũ trước khi thêm dữ liệu mới
                    document.getElementById('ordersContainer').innerHTML = '';
                    // Duyệt qua từng đơn hàng và hiển thị thông tin

                    let table = '<table class="table table-striped mt-5">';
                        table += `<tr>
                                    <th scope="col">STT</th>
                                    <th scope="col">Tên người dùng</th>
                                    <th scope="col">SĐT</th>
                                    <th scope="col">Địa chỉ</th>
                                    <th scope="col">Note</th>
                                    
                                    <th scope="col">Tổng tiền</th>
                                    <th scope="col">Trạng thái</th>
                                    <th scope="col">Chức năng</th>
                                </tr>`;
                    data.forEach(order => {
                        stt++
                        table +=`
                            <tr>
                                <th scope="row">${stt}</th>
                                <td>${order.userId.username}</td>
                                <td>${order.userId.numberphone}</td>
                                <td>${order.userId.address}</td>
                                <td style="overflow:hidden;max-width: 200px;text-overflow: ellipsis;">${order.note}</td>
                                
                                <td>${order.totalMoney}</td>
                                <td>${order.status}</td>
                                <td>
                                    <button class='btn btn-primary' onclick="viewOrderDetails(${order.orderId})">Xem chi tiết</button>
                                </td>
                            </tr>
                        `;
                    });
                    table += '</table>';
                    document.getElementById('ordersContainer').innerHTML = table;
                })
                .catch(error => console.error('Lỗi:', error));
        }

        
        function viewOrderDetails(orderId) {
            window.location.href = `orderdetails.html?orderId=${orderId}`;
        }
        loaddataa()
function loaddataa(){
    
    fetch('http://localhost:8085/admin/users/test', {
        method: 'GET',
        headers: headers
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Failed to fetch user data');
        }
    })
    .then(datas => {
        console.log(datas)
        inputUserName.value=datas.username
        email.value=datas.email
        inputAddress.value=datas.address
        inputPhoneNumber.value=datas.numberphone
        checkdata(datas)
    })
    .catch(error => {
        console.error('Error:', error);
    });
    
    
}
        // send mã otp
        function sendOTP() {
         
        
         
        
            // Gửi yêu cầu POST đến API
            fetch('http://localhost:8085/api/forgetpass', {
                method: 'POST',
                headers: {
                    'Authorization': 'Bearer ' + token,
                    'Content-Type': 'application/json'
                },
             
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to send OTP');
                }
            
            })
            .then(data => {
                // Xử lý phản hồi từ máy chủ (nếu cần)
                alert('Mã OTP đã được gửi thành công');
            })
            .catch(error => {
                console.error('Error:', error);
            });
        }
        function sendOTPAndResetPassword() {
            // Lấy giá trị từ các trường input
            const otp = document.getElementById('otp').value;
            const newPassword = document.getElementById('pass').value;
            console.log(newPassword);
            // Gửi mã OTP và mật khẩu mới đến API
            const data = {
                
                newPassword: newPassword
            };
        
            const jwtToken = localStorage.getItem('jwtToken');
            const headers = {
                'Authorization': `Bearer ${jwtToken}`,
                'Content-Type': 'application/json'
            };
        
            fetch('http://localhost:8085/api/submitpass?otp='+otp, {
                method: 'POST',
                headers: headers,
                body: JSON.stringify(data)
            })
            .then(response => {
                if (response.ok) {
                    return response.text();
                } else {
                    throw new Error('Failed to submit password');
                }
            })
            .then(responseData => {
                // Xử lý kết quả trả về nếu cần
                console.log(responseData);
                alert('Đổi mật khẩu thành công!');
              
             
                // Tải lại trang sau khi đổi mật khẩu thành công
                window.location.reload();
            })
            
        }
        
        
        // Hàm để cập nhật trạng thái đơn hàng
       

        // Gọi hàm để lấy dữ liệu và hiển thị khi trang được tải
        window.onload = fetchDataAndDisplay();
    
        
        