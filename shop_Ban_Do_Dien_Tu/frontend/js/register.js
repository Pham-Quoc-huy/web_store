document.addEventListener("DOMContentLoaded", function () {
  const form = document.querySelector(".auth-form");

  form.addEventListener("submit", function (e) {
    e.preventDefault();

    const fullname = document.getElementById("fullname").value;
    const email = document.getElementById("email").value;
    const phone = document.getElementById("phone").value;
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirm-password").value;

    if (password !== confirmPassword) {
      alert("Mật khẩu nhập lại không khớp!");
      return;
    }

    const user = {
      fullName: fullname,
      email: email,
      phone: phone,
      username: username,
      password: password
    };

    fetch("http://localhost:8088/api/users/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(user)
    })
    .then(res => {
      if (!res.ok) throw new Error("Đăng ký thất bại!");
      return res.json();
    })
    .then(data => {
      alert("Đăng ký thành công! Mời bạn đăng nhập.");
      window.location.href = "login.html";
    })
    .catch(err => {
      alert("Lỗi: " + err.message);
    });
  });
});
