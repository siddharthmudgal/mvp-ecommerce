// displays welcome message on the product list page
function welcome_message() {
    document.getElementById("welcome-header").innerHTML = 'Product Details -> ' + window.localStorage.getItem('item-to-view');
}


welcome_message();