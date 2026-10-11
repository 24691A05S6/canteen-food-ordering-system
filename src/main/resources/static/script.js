const API_BASE = "http://localhost:8081/api";

let cart = JSON.parse(localStorage.getItem("cart")) || [];

function updateCartBadge() {
    const badge = document.getElementById("cartCount");
    if (badge) {
        const totalQty = cart.reduce((sum, item) => sum + item.quantity, 0);
        badge.textContent = totalQty;
    }
}

function saveCart() {
    localStorage.setItem("cart", JSON.stringify(cart));
    updateCartBadge();
}

function addToCart(food) {
    const existing = cart.find(item => item.id === food.id);
    if (existing) {
        existing.quantity += 1;
    } else {
        cart.push({
            id: food.id,
            name: food.name,
            price: food.price,
            imageUrl: food.imageUrl,
            quantity: 1
        });
    }
    saveCart();
    alert(food.name + " added to cart!");
}

async function fetchAllFoods() {
    try {
        const response = await fetch(`${API_BASE}/foods`);
        return await response.json();
    } catch (error) {
        console.error("Error fetching foods:", error);
        return [];
    }
}

async function fetchFoodsByCategory(category) {
    try {
        const response = await fetch(`${API_BASE}/foods/category/${category}`);
        return await response.json();
    } catch (error) {
        console.error("Error fetching by category:", error);
        return [];
    }
}

async function searchFoods(keyword) {
    try {
        const response = await fetch(`${API_BASE}/foods/search?keyword=${encodeURIComponent(keyword)}`);
        return await response.json();
    } catch (error) {
        console.error("Error searching foods:", error);
        return [];
    }
}

function renderFoodCards(foods, containerId) {
    const container = document.getElementById(containerId);
    if (!container) return;

    if (foods.length === 0) {
        container.innerHTML = '<p style="text-align:center; width:100%; color:#888;">No food items found.</p>';
        return;
    }

    container.innerHTML = foods.map(food => `
        <div class="food-card">
            <img src="${food.imageUrl}" alt="${food.name}" onerror="this.src='https://via.placeholder.com/230x150?text=Food'">
            <div class="food-card-body">
                <h5>${food.name}</h5>
                <p class="desc">${food.description || ''}</p>
                <div class="food-card-footer">
                    <span class="food-price">₹${food.price.toFixed(2)}</span>
                    ${food.available
        ? `<button class="btn-add-cart" onclick='addToCart(${JSON.stringify(food)})'>Add to Cart</button>`
        : `<span class="badge badge-unavailable">Unavailable</span>`
    }
                </div>
            </div>
        </div>
    `).join('');
}

function increaseQty(id) {
    const item = cart.find(i => i.id === id);
    if (item) {
        item.quantity += 1;
        saveCart();
        renderCart();
    }
}

function decreaseQty(id) {
    const item = cart.find(i => i.id === id);
    if (item) {
        item.quantity -= 1;
        if (item.quantity <= 0) {
            cart = cart.filter(i => i.id !== id);
        }
        saveCart();
        renderCart();
    }
}

function removeFromCart(id) {
    cart = cart.filter(i => i.id !== id);
    saveCart();
    renderCart();
}

function getCartTotal() {
    return cart.reduce((sum, item) => sum + item.price * item.quantity, 0);
}

function renderCart() {
    const container = document.getElementById("cartItems");
    const totalEl = document.getElementById("cartTotal");
    const subtotalEl = document.getElementById("cartSubtotal");
    const checkoutBtn = document.getElementById("checkoutBtn");
    if (!container) return;

    if (cart.length === 0) {
        container.innerHTML = '<tr><td colspan="5" class="text-center text-muted py-4">Your cart is empty. <a href="menu.html">Browse the menu</a></td></tr>';
        if (totalEl) totalEl.textContent = "₹0.00";
        if (subtotalEl) subtotalEl.textContent = "₹0.00";
        if (checkoutBtn) checkoutBtn.classList.add("disabled");
        return;
    }

    container.innerHTML = cart.map(item => `
        <tr>
            <td>
                <div class="d-flex align-items-center gap-2">
                    <img src="${item.imageUrl}" alt="${item.name}" style="width:50px;height:50px;object-fit:cover;border-radius:8px;" onerror="this.src='https://via.placeholder.com/50?text=Food'">
                    <strong>${item.name}</strong>
                </div>
            </td>
            <td>₹${item.price.toFixed(2)}</td>
            <td>
                <div class="d-flex align-items-center gap-2">
                    <button class="btn btn-sm btn-outline-secondary" onclick="decreaseQty(${item.id})">−</button>
                    <span>${item.quantity}</span>
                    <button class="btn btn-sm btn-outline-secondary" onclick="increaseQty(${item.id})">+</button>
                </div>
            </td>
            <td><strong>₹${(item.price * item.quantity).toFixed(2)}</strong></td>
            <td><button class="btn btn-sm btn-outline-danger" onclick="removeFromCart(${item.id})">Remove</button></td>
        </tr>
    `).join('');

    const total = getCartTotal();
    if (subtotalEl) subtotalEl.textContent = "₹" + total.toFixed(2);
    if (totalEl) totalEl.textContent = "₹" + total.toFixed(2);
    if (checkoutBtn) checkoutBtn.classList.remove("disabled");
}
async function placeOrder(orderData) {
    try {
        const response = await fetch(`${API_BASE}/orders`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(orderData)
        });
        if (!response.ok) {
            throw new Error("Order failed");
        }
        return await response.json();
    } catch (error) {
        console.error("Error placing order:", error);
        return null;
    }
}
async function fetchOrdersByStudent(studentId) {
    try {
        const response = await fetch(`${API_BASE}/orders/student/${encodeURIComponent(studentId)}`);
        return await response.json();
    } catch (error) {
        console.error("Error fetching orders:", error);
        return [];
    }
}

async function fetchOrderById(id) {
    try {
        const response = await fetch(`${API_BASE}/orders/${id}`);
        if (!response.ok) return null;
        return await response.json();
    } catch (error) {
        console.error("Error fetching order:", error);
        return null;
    }
}

function statusBadgeClass(status) {
    return "status-" + status.toLowerCase();
}

document.addEventListener("DOMContentLoaded", () => {
    updateCartBadge();
    renderCart();
});