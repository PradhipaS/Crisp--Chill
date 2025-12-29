// Cart Management System with Login Requirement

// Check if user is logged in
function isUserLoggedIn() {
    return localStorage.getItem('userLoggedIn') === 'true';
}

// Get user data
function getUserData() {
    const userData = localStorage.getItem('userData');
    return userData ? JSON.parse(userData) : null;
}

// Get cart items from localStorage
function getCartItems() {
    const cartItems = localStorage.getItem('cartItems');
    return cartItems ? JSON.parse(cartItems) : [];
}

// Save cart items to localStorage
function saveCartItems(items) {
    localStorage.setItem('cartItems', JSON.stringify(items));
    updateCartDisplay();
}

// Add item to cart function
function addToCart(itemName, price, image = '') {
    // Check if user is logged in
    if (!isUserLoggedIn()) {
        // Show login required popup
        showLoginRequiredPopup();
        return;
    }

    // Get current cart items
    let cartItems = getCartItems();
    
    // Check if item already exists in cart
    const existingItemIndex = cartItems.findIndex(item => item.name === itemName);
    
    if (existingItemIndex > -1) {
        // Item exists, increase quantity
        cartItems[existingItemIndex].quantity += 1;
        showNotification(`${itemName} quantity updated in cart! 🛒`);
    } else {
        // Add new item to cart
        const newItem = {
            id: Date.now(), // Simple ID generation
            name: itemName,
            price: price,
            image: image,
            quantity: 1,
            addedAt: new Date().toISOString()
        };
        cartItems.push(newItem);
        showNotification(`${itemName} added to cart! 🛒`);
    }
    
    // Save updated cart
    saveCartItems(cartItems);
}

// Show login required popup
function showLoginRequiredPopup() {
    // Create popup overlay
    const popupOverlay = document.createElement('div');
    popupOverlay.className = 'login-required-popup';
    popupOverlay.innerHTML = `
        <div class="login-popup-content">
            <div class="login-popup-header">
                <h2>🔐 Login Required</h2>
                <button class="close-popup" onclick="closeLoginPopup()">&times;</button>
            </div>
            <div class="login-popup-body">
                <div class="popup-icon">🛒</div>
                <p>You need to be logged in to add items to your cart!</p>
                <div class="popup-benefits">
                    <div class="benefit">✅ Save your favorite items</div>
                    <div class="benefit">✅ Quick checkout process</div>
                    <div class="benefit">✅ Order history tracking</div>
                </div>
            </div>
            <div class="login-popup-buttons">
                <button class="popup-btn login-btn" onclick="goToLogin()">
                    🔑 Login Now
                </button>
                <button class="popup-btn signup-btn" onclick="goToSignup()">
                    📝 Sign Up
                </button>
            </div>
        </div>
    `;
    
    document.body.appendChild(popupOverlay);
    
    // Add event listener to close popup when clicking outside
    popupOverlay.addEventListener('click', function(e) {
        if (e.target === popupOverlay) {
            closeLoginPopup();
        }
    });
}

// Close login popup
function closeLoginPopup() {
    const popup = document.querySelector('.login-required-popup');
    if (popup) {
        popup.remove();
    }
}

// Navigate to login page
function goToLogin() {
    window.location.href = 'login.html';
}

// Navigate to signup page
function goToSignup() {
    window.location.href = 'signup.html';
}

// Show notification
function showNotification(message) {
    // Create notification element
    const notification = document.createElement('div');
    notification.className = 'cart-notification';
    notification.innerHTML = `
        <div class="notification-content">
            <span class="notification-icon">✅</span>
            <span class="notification-text">${message}</span>
        </div>
    `;
    
    document.body.appendChild(notification);
    
    // Remove notification after 3 seconds
    setTimeout(() => {
        notification.remove();
    }, 3000);
}

// Update cart display (cart count in navigation)
function updateCartDisplay() {
    const cartItems = getCartItems();
    const cartCount = cartItems.reduce((total, item) => total + item.quantity, 0);
    
    // Update cart count in navigation if element exists
    const cartCountElement = document.getElementById('cart-count');
    if (cartCountElement) {
        cartCountElement.textContent = cartCount;
        cartCountElement.style.display = cartCount > 0 ? 'inline' : 'none';
    }
}

// Order Now function (also requires login)
function orderNow(itemName, price) {
    if (!isUserLoggedIn()) {
        showLoginRequiredPopup();
        return;
    }
    
    // Add to cart first
    addToCart(itemName, price);
    
    // Redirect to cart or checkout page
    setTimeout(() => {
        alert(`${itemName} added to cart! Redirecting to checkout...`);
        // You can redirect to a cart/checkout page here
        // window.location.href = 'cart.html';
    }, 1000);
}

// Initialize cart display when page loads
document.addEventListener('DOMContentLoaded', function() {
    updateCartDisplay();
    
    // Show welcome message if user is logged in
    if (isUserLoggedIn()) {
        const userData = getUserData();
        if (userData && userData.firstName) {
            console.log(`Welcome back, ${userData.firstName}!`);
        }
    }
});

// CSS Styles for popups and notifications
const cartStyles = `
<style>
/* Login Required Popup */
.login-required-popup {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.8);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 10000;
    animation: fadeIn 0.3s ease;
}

.login-popup-content {
    background: white;
    border-radius: 20px;
    padding: 0;
    max-width: 450px;
    width: 90%;
    box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3);
    animation: slideIn 0.4s ease;
    overflow: hidden;
}

.login-popup-header {
    background: linear-gradient(45deg, #ff6b6b, #feca57);
    padding: 25px;
    text-align: center;
    color: white;
    position: relative;
}

.login-popup-header h2 {
    margin: 0;
    font-size: 24px;
}

.close-popup {
    position: absolute;
    top: 15px;
    right: 20px;
    background: none;
    border: none;
    font-size: 30px;
    color: white;
    cursor: pointer;
    width: 40px;
    height: 40px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: background 0.3s;
}

.close-popup:hover {
    background: rgba(255, 255, 255, 0.2);
}

.login-popup-body {
    padding: 30px 25px;
    text-align: center;
}

.popup-icon {
    font-size: 60px;
    margin-bottom: 20px;
}

.login-popup-body p {
    font-size: 18px;
    color: #2c3e50;
    margin-bottom: 25px;
}

.popup-benefits {
    margin-bottom: 25px;
}

.benefit {
    padding: 8px 0;
    color: #27ae60;
    font-weight: 500;
}

.login-popup-buttons {
    display: flex;
    gap: 15px;
    padding: 0 25px 25px;
}

.popup-btn {
    flex: 1;
    padding: 15px;
    border: none;
    border-radius: 12px;
    font-size: 16px;
    font-weight: bold;
    cursor: pointer;
    transition: all 0.3s ease;
}

.login-btn {
    background: linear-gradient(45deg, #74b9ff, #0984e3);
    color: white;
}

.login-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 25px rgba(116, 185, 255, 0.4);
}

.signup-btn {
    background: linear-gradient(45deg, #ff6b6b, #ee5a24);
    color: white;
}

.signup-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 25px rgba(255, 107, 107, 0.4);
}

/* Cart Notification */
.cart-notification {
    position: fixed;
    top: 20px;
    right: 20px;
    background: linear-gradient(45deg, #27ae60, #2ecc71);
    color: white;
    padding: 15px 20px;
    border-radius: 12px;
    box-shadow: 0 5px 15px rgba(39, 174, 96, 0.3);
    z-index: 9999;
    animation: slideInRight 0.4s ease;
}

.notification-content {
    display: flex;
    align-items: center;
    gap: 10px;
}

.notification-icon {
    font-size: 18px;
}

.notification-text {
    font-weight: 500;
}

/* Animations */
@keyframes fadeIn {
    from { opacity: 0; }
    to { opacity: 1; }
}

@keyframes slideIn {
    from { 
        opacity: 0;
        transform: scale(0.7) translateY(-50px);
    }
    to { 
        opacity: 1;
        transform: scale(1) translateY(0);
    }
}

@keyframes slideInRight {
    from {
        opacity: 0;
        transform: translateX(100%);
    }
    to {
        opacity: 1;
        transform: translateX(0);
    }
}

/* Mobile Responsive */
@media (max-width: 768px) {
    .login-popup-buttons {
        flex-direction: column;
    }
    
    .cart-notification {
        right: 10px;
        left: 10px;
        width: auto;
    }
}
</style>
`;

// Add styles to document head
document.head.insertAdjacentHTML('beforeend', cartStyles);