/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function validateForm() {
    let username = document.forms["registerForm"]["username"].value;
    let password = document.forms["registerForm"]["password"].value;
    let email = document.forms["registerForm"]["email"].value;
    let phone = document.forms["registerForm"]["phone"].value;
    let address = document.forms["registerForm"]["address"].value;

    if (username === "" || password === "" || email === "" || phone === "" || address === "") {
        alert("Tất cả giá trị phải được nhập");
        return false;
    }

    let passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{6,}$/;
    if (!password.match(passwordRegex)) {
        alert("Mật khẩu phải có ít nhất 6 kí tự, trong đó có ít nhất 1 số,1 chữ in hoa,1 chữ thường và 1 kí tự đặc biêt");
        return false;
    }

    let phoneRegex = /^0[1-9]\d{8}$/;
    if (!phone.match(phoneRegex)) {
        alert("Số điện thoại phải có 10 số, bắt đầu bằng số 0 và số liền kề sau đó không được là số 0");
        return false;
    }

    return true;
}

