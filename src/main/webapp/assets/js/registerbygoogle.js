/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function validateForm() {
    let username = document.forms["registerForm"]["username"].value;
    let email = document.forms["registerForm"]["email"].value;
    let phone = document.forms["registerForm"]["phone"].value;
    let address = document.forms["registerForm"]["address"].value;

    if (username === "" || email === "" || phone === "" || address === "") {
        alert("Tất cả giá trị phải được nhập");
        return false;
    }

    let phoneRegex = /^0[1-9]\d{8}$/;
    if (!phone.match(phoneRegex)) {
        alert("Số điện thoại phải có 10 số, bắt đầu bằng số 0 và số liền kề sau đó không được là số 0");
        return false;
    }

    return true;
}


