document.addEventListener("DOMContentLoaded", () => {
  const cart = JSON.parse(localStorage.getItem("cart")) || [];
  const cartBody = document.getElementById("cart-body");
  const totalPrice = document.getElementById("total-price");

  function renderCart() {
    cartBody.innerHTML = "";
    let total = 0;

    cart.forEach((item, index) => {
      const itemTotal = item.price * item.quantity;
      total += itemTotal;

      const row = document.createElement("tr");
      row.innerHTML = `
        <td>
          <img src="${item.imageUrl}" alt="${item.name}" width="50" style="vertical-align: middle; margin-right: 8px;">
          ${item.name}
        </td>
        <td>${item.price.toLocaleString()} VND</td>
        <td>
          <input type="number" min="1" value="${item.quantity}" onchange="updateQuantity(${index}, this.value)">
        </td>
        <td>${itemTotal.toLocaleString()} VND</td>
        <td><button onclick="removeItem(${index})">X</button></td>
      `;
      cartBody.appendChild(row);
    });

    totalPrice.textContent = total.toLocaleString();
  }

  window.updateQuantity = function(index, newQty) {
    const quantity = parseInt(newQty);
    if (quantity > 0) {
      cart[index].quantity = quantity;
      localStorage.setItem("cart", JSON.stringify(cart));
      renderCart();
    }
  }

  window.removeItem = function(index) {
    cart.splice(index, 1);
    localStorage.setItem("cart", JSON.stringify(cart));
    renderCart();
  }

  renderCart();
});
