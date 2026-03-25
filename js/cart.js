// Cart Management - uses localStorage as cache, syncs with backend when available

function isUserLoggedIn() { return localStorage.getItem('userLoggedIn') === 'true'; }
function getUserData() { const u = localStorage.getItem('userData'); return u ? JSON.parse(u) : null; }
function getCartItems() { const c = localStorage.getItem('cartItems'); return c ? JSON.parse(c) : []; }
function saveCartItems(items) { localStorage.setItem('cartItems', JSON.stringify(items)); updateCartDisplay(); }

// Add item to cart (localStorage-based, works offline too)
function addToCart(itemName, price, image = '') {
    if (!isUserLoggedIn()) {
        if (confirm('Please login to add items to cart. Go to login page?')) {
            window.location.href = 'login.html';
        }
        return;
    }
    let items = getCartItems();
    const idx = items.findIndex(i => i.name === itemName);
    if (idx > -1) {
        items[idx].quantity += 1;
    } else {
        items.push({ id: Date.now(), name: itemName, price, image, quantity: 1 });
    }
    saveCartItems(items);
    showNotification(`${itemName} added to cart 🛒`);
}

function removeFromCart(itemId) {
    let items = getCartItems().filter(i => i.id !== itemId);
    saveCartItems(items);
}

function updateQuantity(itemId, delta) {
    let items = getCartItems();
    const idx = items.findIndex(i => i.id === itemId);
    if (idx > -1) {
        items[idx].quantity = Math.max(1, items[idx].quantity + delta);
        saveCartItems(items);
    }
}

function clearCart() { saveCartItems([]); }

function getCartTotal() {
    return getCartItems().reduce((sum, i) => sum + i.price * i.quantity, 0);
}

function orderNow(itemName, price) {
    if (!isUserLoggedIn()) {
        if (confirm('Please login to order. Go to login page?')) window.location.href = 'login.html';
        return;
    }
    addToCart(itemName, price);
    setTimeout(() => { window.location.href = 'cart.html'; }, 600);
}

function updateCartDisplay() {
    const items = getCartItems();
    const count = items.reduce((t, i) => t + i.quantity, 0);
    const el = document.getElementById('cart-count');
    if (el) { el.textContent = count; el.style.display = count > 0 ? 'inline' : 'none'; }
}

function showNotification(msg) {
    const n = document.createElement('div');
    n.className = 'cart-notification';
    n.textContent = msg;
    document.body.appendChild(n);
    setTimeout(() => n.remove(), 2800);
}

document.addEventListener('DOMContentLoaded', updateCartDisplay);

// Inject notification style once
const _ns = document.createElement('style');
_ns.textContent = `
.cart-notification {
    position: fixed; top: 20px; right: 20px;
    background: linear-gradient(135deg, #b94981, #87b1d3);
    color: white; padding: 12px 20px; border-radius: 10px;
    font-weight: bold; font-size: 14px; z-index: 9999;
    box-shadow: 0 4px 15px rgba(185,73,129,0.4);
    animation: slideInRight 0.3s ease;
}
@keyframes slideInRight {
    from { opacity:0; transform: translateX(100%); }
    to   { opacity:1; transform: translateX(0); }
}`;
document.head.appendChild(_ns);
