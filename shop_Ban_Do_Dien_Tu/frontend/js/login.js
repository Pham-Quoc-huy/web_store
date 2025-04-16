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
      alert("Đăng nhập thành công!");
      console.log(data);
      window.location.href = "index.html";
    })
    .catch(err => {
      alert(err.message);
    });
  });
});
