document.querySelector(".checkout-form").addEventListener("submit", function (e) {
  e.preventDefault();

  const userId = localStorage.getItem("userId");
  if (!userId) {
    alert("Bạn cần đăng nhập để thanh toán.");
    return;
  }

  const address = document.getElementById("address").value;
  const payment = document.getElementById("payment").value;

  fetch(`http://localhost:8088/orders/checkout`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      userId: parseInt(userId),
      shippingAddress: address,
      paymentMethod: payment
    })
  })
    .then(res => res.text())
    .then(msg => {
      alert(msg);
      window.location.href = "index.html"; // Hoặc loadCart() nếu cần
    })
    .catch(err => console.error("Lỗi khi thanh toán:", err));
});
