// File: admin.js

// ✅ Kiểm tra quyền truy cập
const role = localStorage.getItem("role");
if (role !== "ADMIN") {
  alert("Bạn không có quyền truy cập trang quản trị!");
  window.location.href = "index.html";
}

// ✅ Load sản phẩm vào bảng quản lý
function loadProducts() {
  fetch("http://localhost:8088/api/products")
    .then(res => res.json())
    .then(products => {
      const tbody = document.querySelector(".admin-section:nth-of-type(1) tbody");
      tbody.innerHTML = "";
      products.forEach(product => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${product.id}</td>
          <td>${product.name}</td>
          <td>${product.price.toLocaleString('vi-VN')} VND</td>
          <td>${product.active ? 'Hiển thị' : 'Ẩn'}</td>
          <td>
            <button onclick="editProduct(${product.id})">Sửa</button>
            <button onclick="deleteProduct(${product.id})">Xóa</button>
          </td>
        `;
        tbody.appendChild(row);
      });
    });
}

function deleteProduct(id) {
  if (!confirm("Bạn có chắc muốn xoá sản phẩm này?")) return;
  fetch(`http://localhost:8088/api/products/delete/${id}`, {
    method: "DELETE"
  })
    .then(() => {
      alert("Đã xoá sản phẩm.");
      loadProducts();
    });
}

function editProduct(id) {
  alert("Chức năng đang phát triển: sửa sản phẩm " + id);
  // Có thể redirect sang product-edit.html?id=...
}

// ✅ Load đơn hàng vào bảng quản lý
function loadOrders() {
  fetch("http://localhost:8088/orders")
    .then(res => res.json())
    .then(orders => {
      const tbody = document.querySelector(".admin-section:nth-of-type(2) tbody");
      tbody.innerHTML = "";
      orders.forEach(order => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>#${order.id}</td>
          <td>${order.user.fullName || "(Không rõ)"}</td>
          <td>${new Date(order.orderDate).toLocaleDateString()}</td>
          <td>${order.totalAmount.toLocaleString()} VND</td>
          <td>${order.status}</td>
          <td>
            <button onclick="updateOrderStatus(${order.id})">Cập nhật</button>
          </td>
        `;
        tbody.appendChild(row);
      });
    });
}

function updateOrderStatus(orderId) {
  const newStatus = prompt("Nhập trạng thái mới (PROCESSING, SHIPPED, DELIVERED)");
  if (!newStatus) return;

  fetch(`http://localhost:8088/orders/${orderId}/status`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ status: newStatus })
  })
    .then(res => res.text())
    .then(msg => {
      alert(msg);
      loadOrders();
    });
}

// ✅ Tự động tải dữ liệu khi trang load
window.addEventListener("DOMContentLoaded", () => {
  loadProducts();
  loadOrders();
});
