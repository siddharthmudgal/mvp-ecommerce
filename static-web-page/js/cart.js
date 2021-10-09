// display welcome message
function display_welcome_message() {
    document.getElementById("welcome-header").innerHTML = 'Cart for ' + window.localStorage.getItem('user-name');
}


// call rest api to load cart elements for user
function load_cart_for_user() {
    $(document).ready(function() {
        var load_cart_url = 'http://localhost:8080/users/' + window.localStorage.getItem('user-uuid') + '/cart';
        $.getJSON(load_cart_url, function(data) {

            var t = $('#mycart').DataTable({
                columnDefs: [{
                        "className": "dt-center",
                        "targets": "_all"
                    },
                    {
                        "targets": -1,
                        "data": null,
                        "defaultContent": "<button id=\"remove\">-</button>"
                    },
                    {
                        "targets": -2,
                        "data": null,
                        "defaultContent": "<button id=\"add\">+</button>"
                    },
                    {
                        "targets": [0],
                        "visible": false,
                        "searchable": false
                    }
                ]
            });

            for (var i = 0; i < data.length; i++) {
                var product = data[i]["productDO"];
                t.row.add(
                    [
                        product["uuid"],
                        product["name"],
                        product["price"],
                        data[i]["quantity"]
                    ]
                ).draw(false);
            }

            // define onclick function for buttons
            $('#mycart tbody').on('click', 'button', function() {

                var id = this.id;
                var data = t.row($(this).parents('tr')).data();

                if (id == 'add') {
                    var add_to_cart_url = "http://localhost:8080/users/" + window.localStorage.getItem('user-uuid') + "/addtocart/" + data[0];

                    $.post(add_to_cart_url, function(data) {
                        alert("Added to cart");
                        location.reload();
                    });
                }


                if (id == 'remove') {
                    var remove_from_cart_url = "http://localhost:8080/users/" + window.localStorage.getItem('user-uuid') + "/removefromcart/" + data[0];

                    $.post(remove_from_cart_url, function(data) {
                        alert("Removed from cart");
                        location.reload();
                    });
                }

            });
        });
    });
}

display_welcome_message();
load_cart_for_user();