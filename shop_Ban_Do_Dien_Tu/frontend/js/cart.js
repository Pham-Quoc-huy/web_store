document.addEventListener("DOMContentLoaded", loadCart);

function loadCart() {
  const userId = localStorage.getItem("userId");
  if (!userId) {
    document.getElementById("cart-body").innerHTML = "<tr><td colspan='5'>Bạn chưa đăng nhập.</td></tr>";
    return;
  }

  fetch(`http://localhost:8088/cart-items/user/${userId}`)
    .then(res => res.json())
    .then(data => {
      const tbody = document.getElementById("cart-body");
      const totalPriceEl = document.getElementById("total-price");
      tbody.innerHTML = "";

      if (data.length === 0) {
        tbody.innerHTML = "<tr><td colspan='5'>Giỏ hàng của bạn đang trống.</td></tr>";
        totalPriceEl.textContent = "0";
        return;
      }

      let total = 0;
      data.forEach(item => {
        const subtotal = item.product.price * item.quantity;
        total += subtotal;

        const row = document.createElement("tr");
        row.innerHTML = `
          <td style="display: flex; align-items: center; gap: 10px;">
            <img src="${item.product.imageUrl}" alt="${item.product.name}" width="50" height="50" style="object-fit: cover; border-radius: 6px;">
            <span>${item.product.name}</span>
          </td>
          <td>${item.product.price.toLocaleString('vi-VN')} VND</td>
          <td>
            <div style="display: flex; align-items: center; gap: 5px;">
              <input
                type="number"
                value="${item.quantity}"
                min="1"
                style="width: 50px; text-align: center; padding: 4px;"
                onchange="updateQuantity(${item.id}, this.value)">
            </div>
          </td>
          <td>${subtotal.toLocaleString('vi-VN')} VND</td>
          <td><button onclick="removeFromCart(${item.id})">Xóa</button></td>
        `;
        tbody.appendChild(row);
      });

      totalPriceEl.textContent = total.toLocaleString('vi-VN');
    })
    .catch(err => console.error("Lỗi khi tải giỏ hàng:", err));
}

function updateQuantity(cartItemId, newQuantity) {
  newQuantity = parseInt(newQuantity);
  if (isNaN(newQuantity) || newQuantity < 1) return;

  fetch(`http://localhost:8088/cart-items/update/${cartItemId}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ quantity: newQuantity })
  })
    .then(() => loadCart())
    .catch(err => console.error("Lỗi cập nhật số lượng:", err));
}

function removeFromCart(id) {
  fetch(`http://localhost:8088/cart-items/delete/${id}?cartId=0`, {
    method: "GET",
  })
    .then(res => res.text())
    .then(msg => {
      alert(msg);
      loadCart();
    })
    .catch(err => console.error("Lỗi khi xoá sản phẩm:", err));
}
