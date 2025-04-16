
document.addEventListener("DOMContentLoaded", function () {
  const productsContainer = document.getElementById("products");

  fetch("http://localhost:8088/api/products")
    .then(res => {
      if (!res.ok) throw new Error("Không thể tải danh sách sản phẩm");
      return res.json();
    })
    .then(products => {
      products.forEach(product => {
        const card = document.createElement("div");
        card.className = "product-card";

        card.innerHTML = `
          <img src="img/${product.image || 'default.png'}" alt="${product.name}" />
          <h3>${product.name}</h3>
          <p>Giá: ${product.price.toLocaleString()} VND</p>
          <button onclick="location.href='product.html?id=${product.id}'">Xem chi tiết</button>
        `;

        productsContainer.appendChild(card);
      });
    })
    .catch(err => {
      productsContainer.innerHTML = `<p style="color:red;">${err.message}</p>`;
    });
});
