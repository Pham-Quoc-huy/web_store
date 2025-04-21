document.addEventListener("DOMContentLoaded", function () {
  fetch("http://localhost:8088/api/products")
    .then(response => response.json())
    .then(data => {
      const productList = document.getElementById("productList");
      productList.innerHTML = "";

      data.forEach(product => {
        const card = document.createElement("div");
        card.className = "product-card";

        card.innerHTML = `
          <img src="${product.imageUrl}" alt="${product.name}">
          <div class="product-name">${product.name}</div>
          <div class="product-price">${product.price} VND</div>
          <button onclick="goToProduct(${product.id})">Mua ngay</button>
        `;

        productList.appendChild(card);
      });
    })
    .catch(error => {
      console.error("Lỗi khi tải sản phẩm:", error);
      document.getElementById("productList").innerHTML = "<p>Lỗi tải sản phẩm!</p>";
    });
});

function goToProduct(productId) {
  window.location.href = `product.html?id=${productId}`;
}
