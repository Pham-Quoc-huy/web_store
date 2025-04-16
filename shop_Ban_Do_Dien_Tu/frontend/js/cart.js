document.addEventListener("DOMContentLoaded", () => {
  const cartBody = document.getElementById("cart-body");
  const totalPriceSpan = document.getElementById("total-price");

  // Lấy giỏ hàng từ localStorage (dạng array sản phẩm)
  let cart = JSON.parse(localStorage.getItem("cart")) || [];

  function renderCart() {
    cartBody.innerHTML = "";
    let total = 0;

    if (cart.length === 0) {
      cartBody.innerHTML = '<tr><td colspan="5">Giỏ hàng trống</td></tr>';
      totalPriceSpan.textContent = "0";
      return;
    }

    cart.forEach((item, index) => {
      const row = document.createElement("tr");

      const itemTotal = item.price * item.quantity;
      total += itemTotal;

      row.innerHTML = `
        <td>${item.name}</td>
        <td>${item.price.toLocaleString()} VND</td>
        <td>${item.quantity}</td>
        <td>${itemTotal.toLocaleString()} VND</td>
        <td><button data-index="${index}" class="remove-btn">X</button></td>
      `;

      cartBody.appendChild(row);
    });

    totalPriceSpan.textContent = total.toLocaleString();
  }

  // Xử lý nút xoá
  cartBody.addEventListener("click", function (e) {
    if (e.target.classList.contains("remove-btn")) {
      const index = e.target.dataset.index;
      cart.splice(index, 1);
      localStorage.setItem("cart", JSON.stringify(cart));
      renderCart();
    }
  });

  renderCart();
});
