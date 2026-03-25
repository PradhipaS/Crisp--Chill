// API Configuration - connects frontend to Spring Boot backend
const API_BASE = 'http://localhost:8080/api';

// ── Auth helpers ──────────────────────────────────────────────
function getUser() {
    const u = localStorage.getItem('userData');
    return u ? JSON.parse(u) : null;
}
function isLoggedIn() {
    return localStorage.getItem('userLoggedIn') === 'true';
}
function setSession(user) {
    localStorage.setItem('userLoggedIn', 'true');
    localStorage.setItem('userData', JSON.stringify(user));
}
function clearSession() {
    localStorage.removeItem('userLoggedIn');
    localStorage.removeItem('userData');
    localStorage.removeItem('cartItems');
}

// ── User API ──────────────────────────────────────────────────
async function apiSignup(data) {
    const res = await fetch(`${API_BASE}/users/signup`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });
    return res.json();
}

async function apiLogin(data) {
    const res = await fetch(`${API_BASE}/users/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });
    return res.json();
}

// ── Cart API ──────────────────────────────────────────────────
async function apiGetCart(userId) {
    const res = await fetch(`${API_BASE}/cart/user/${userId}`);
    return res.json();
}

async function apiAddToCart(userId, productId, quantity = 1) {
    const res = await fetch(`${API_BASE}/cart`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userId, productId, quantity })
    });
    return res.json();
}

async function apiUpdateCart(cartId, quantity) {
    const res = await fetch(`${API_BASE}/cart/${cartId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ quantity })
    });
    return res.json();
}

async function apiRemoveFromCart(cartId) {
    const res = await fetch(`${API_BASE}/cart/${cartId}`, { method: 'DELETE' });
    return res.json();
}

async function apiClearCart(userId) {
    const res = await fetch(`${API_BASE}/cart/user/${userId}`, { method: 'DELETE' });
    return res.json();
}

// ── Orders API ────────────────────────────────────────────────
async function apiPlaceOrder(data) {
    const res = await fetch(`${API_BASE}/orders`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });
    return res.json();
}

async function apiGetOrders(userId) {
    const res = await fetch(`${API_BASE}/orders/user/${userId}`);
    return res.json();
}

// ── Products API ──────────────────────────────────────────────
async function apiGetProducts(categoryId) {
    const url = categoryId
        ? `${API_BASE}/products/category/${categoryId}`
        : `${API_BASE}/products`;
    const res = await fetch(url);
    return res.json();
}

async function apiSearchProducts(query) {
    const res = await fetch(`${API_BASE}/products/search?name=${encodeURIComponent(query)}`);
    return res.json();
}
