// This script populates the users via a REST API for the select element
function populate_users_drop_down() {
    let dropdown = document.getElementById('users-dropdown');
    dropdown.length = 0;

    dropdown.selectedIndex = 0;

    const url = 'http://localhost:8080/users';

    const request = new XMLHttpRequest();
    request.open('GET', url, true);

    request.onload = function() {
        if (request.status === 200) {
            const data = JSON.parse(request.responseText);
            let option;
            for (let i = 0; i < data.length; i++) {
                option = document.createElement('option');
                option.text = data[i].username;
                option.value = data[i].uuid;
                dropdown.add(option);
            }

            browse_to_product_list();

        }
    }


    request.onerror = function() {
        console.error('An error occurred fetching the JSON from ' + url);
    };

    request.send();
}

// This function stores the user_id selected
function browse_to_product_list() {
    document.getElementById("product-list-page-button").onclick = function() {
        var e = document.getElementById("users-dropdown");
        var user_name = e.options[e.selectedIndex].text;
        var user_uuid = e.options[e.selectedIndex].value;

        window.localStorage.setItem('user-uuid', user_uuid);
        window.localStorage.setItem('user-name', user_name);

        window.location.href = './products-list.html'
    };
}

populate_users_drop_down();