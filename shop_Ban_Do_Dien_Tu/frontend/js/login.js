document.addEventListener("DOMContentLoaded", function () {

  const form = document.querySelector(".auth-form");

  form.addEventListener("submit", function (e) {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch("http://localhost:8088/api/users/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"

      },
      body: JSON.stringify({ username, password })
    })
    .then(res => {
      if (!res.ok) throw new Error("Đăng nhập thất bại");
      return res.json();
    })
   .then(data => {
     console.log("Dữ liệu trả về từ backend:", data);
     alert("Đăng nhập thành công!");
     localStorage.setItem("userId", data.id);
     localStorage.setItem("username", data.username);
     localStorage.setItem("role", data.role); // 👈 THÊM DÒNG NÀY

     if (data.role === "ADMIN") {
       window.location.href = "admin.html"; // 👉 chuyển sang trang admin
     } else {
       window.location.href = "index.html"; // 👉 người dùng thường
     }
   })


    .catch(err => {
      alert(err.message);
    });
  });
});
