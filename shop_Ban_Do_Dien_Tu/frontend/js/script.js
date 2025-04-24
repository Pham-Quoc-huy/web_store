function loadAllProducts() {
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
          <div class="product-price">${product.price.toLocaleString('vi-VN')} VND</div>
          <button onclick="goToProduct(${product.id})">Mua ngay</button>
        `;
        productList.appendChild(card);
      });
    })
    .catch(error => {
      console.error("Lỗi khi tải sản phẩm:", error);
      document.getElementById("productList").innerHTML = "<p>Lỗi tải sản phẩm!</p>";
    });
}

document.addEventListener("DOMContentLoaded", loadAllProducts);

function goToProduct(productId) {
  window.location.href = `product.html?id=${productId}`;
}

function searchProducts() {
  const keyword = document.getElementById("search-input").value.trim();

  if (!keyword) {
    loadAllProducts(); // Reset về toàn bộ nếu không có từ khóa
    return;
  }

  fetch(`http://localhost:8088/api/products/search?q=${encodeURIComponent(keyword)}`)
    .then(res => res.json())
    .then(data => {
      const productList = document.getElementById("productList");
      productList.innerHTML = "";

      if (data.length === 0) {
        productList.innerHTML = "<p>Không tìm thấy sản phẩm nào.</p>";
        return;
      }

      data.forEach(product => {
        const card = document.createElement("div");
        card.className = "product-card";
        card.innerHTML = `
          <img src="${product.imageUrl}" alt="${product.name}">
          <div class="product-name">${product.name}</div>
          <div class="product-price">${product.price.toLocaleString('vi-VN')} VND</div>
          <button onclick="goToProduct(${product.id})">Mua ngay</button>
        `;
        productList.appendChild(card);
      });
    })
    .catch(err => console.error("Lỗi tìm kiếm:", err));
}
function filterByCategory(categoryId) {
  if (categoryId === 0) {
    loadAllProducts(); // nếu chọn “Tất cả”
    return;
  }

  fetch(`http://localhost:8088/api/products/category/${categoryId}`)
    .then(res => res.json())
    .then(data => {
      const productList = document.getElementById("productList");
      productList.innerHTML = "";

      if (data.length === 0) {
        productList.innerHTML = "<p>Không có sản phẩm trong danh mục này.</p>";
        return;
      }

      data.forEach(product => {
        const card = document.createElement("div");
        card.className = "product-card";
        card.innerHTML = `
          <img src="${product.imageUrl}" alt="${product.name}">
          <div class="product-name">${product.name}</div>
          <div class="product-price">${product.price.toLocaleString('vi-VN')} VND</div>
          <button onclick="goToProduct(${product.id})">Mua ngay</button>
        `;
        productList.appendChild(card);
      });
    })
    .catch(err => console.error("Lỗi khi lọc theo danh mục:", err));
}
function addToCart(productId) {
  const userId = localStorage.getItem("userId");

  if (!userId) {
    alert("Bạn cần đăng nhập để thêm sản phẩm vào giỏ hàng.");
    return;
  }

  fetch("http://localhost:8088/cart-items/add", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      userId: parseInt(userId),
      productId: productId,
      quantity: 1
    })
  })
  .then(res => res.text())
  .then(msg => alert(msg))
  .catch(err => console.error("Lỗi khi thêm giỏ hàng:", err));
}
document.addEventListener("DOMContentLoaded", () => {
    const username = localStorage.getItem("username");
    const greetingEl = document.getElementById("user-greeting");
    if (username && greetingEl) {
      greetingEl.textContent = `Xin chào, ${username} 👋`;
    }
  });
   document.addEventListener("DOMContentLoaded", () => {
        const username = localStorage.getItem("username");
        const greeting = document.getElementById("user-greeting");
        const loginLink = document.getElementById("login-link");
        const registerLink = document.getElementById("register-link");
        const logoutLink = document.getElementById("logout-link");

        if (username) {
          greeting.textContent = `Xin chào, ${username} 👋`;
          loginLink.style.display = "none";
          registerLink.style.display = "none";
          logoutLink.style.display = "inline";
        }

        logoutLink.addEventListener("click", function (e) {
          e.preventDefault();
          localStorage.removeItem("userId");
          localStorage.removeItem("username");
          window.location.reload();
        });
      });