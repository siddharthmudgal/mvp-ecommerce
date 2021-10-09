// displays welcome message on the product list page
function welcome_message() {
    document.getElementById("welcome-header").innerHTML = 'Welcome ' + window.localStorage.getItem('user-name');
}


// load all the products
function load_products() {
    $(document).ready(function() {
        var url_load_all_products = "http://localhost:8080/products";
        $.getJSON(url_load_all_products, function(data) {
            var t = $('#products').DataTable({
                columnDefs: [{
                        "className": "dt-center",
                        "targets": "_all"
                    },
                    {
                        "targets": -1,
                        "data": null,
                        "defaultContent": "<button id=\"add\">Add to cart</button>"
                    },
                    {
                        "targets": -2,
                        "data": null,
                        "defaultContent": "<button id=\"details\">Details</button>"
                    },
                    {
                        "targets": [0],
                        "visible": false,
                        "searchable": false
                    }

                ]
            });

            // parse over data recvd to populate table
            for (var i = 0; i < data.length; i++) {
                t.row.add(
                    [
                        data[i]["uuid"],
                        data[i]["name"],
                        data[i]["description"],
                        data[i]["price"],
                        data[i]["quantity"]
                    ]
                ).draw(false);
            }

            // define onclick functions for each button
            $('#products tbody').on('click', 'button', function() {
                var id = this.id;
                var data = t.row($(this).parents('tr')).data();

                if (id == 'add') {

                    var url = "http://localhost:8080/users/" + window.localStorage.getItem('user-uuid') + "/addtocart/" + data[0];

                    $.post(url, function(data) {
                        alert("Added to cart");
                    });
                }
                if (id == 'details') {
                    window.location.href = './product-detail.html'
                    window.localStorage.setItem('item-to-view', data);
                }
            });
        });
    });
}

welcome_message();
load_products();