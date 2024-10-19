
var token = localStorage.getItem('token');

$(document).ready(function() {

    loadAllInventory();

    loadInventoryCode('IIM');
    addRowToTable();

});

$('#inventoryForm').on('submit', function(e) {
    e.preventDefault(); // Prevent default form submission

    var formData = new FormData(this);

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/inventory', // Adjust the URL as per your API
        data: formData,
        contentType: false,
        headers: {
            'Authorization': 'Bearer ' + token
        },
        processData: false,
        success: function(response) {
            Swal.fire({
                icon: 'success',
                title: 'Saved Successfully',
                text: response
            });

            loadAllInventory();
        },
        error: function(xhr, status, error) {
            Swal.fire({
                icon: 'error',
                title: 'EROOR',
                text: status,xhr,error
            });
        }
    });
});


    // Call this function to load the inventory code when the page loads
 // Replace 'IIM' with your desired prefix

    // Function to generate and load inventory code
    function loadInventoryCode(prefix) {
        $.ajax({
            url: `http://localhost:8080/inventory/generateInventoryCode?prefix=${prefix}`,
            method: "GET",
            headers: {
                'Authorization': 'Bearer ' + token // Ensure you have the token variable available
            },
            success: function (newCode) {
                console.log('Generated Inventory Code:', newCode);
                $('#itemCode').val(newCode); // Populate the input field with the new code
            },
            error: function (xhr, status, error) {
                console.error('Error generating inventory code:', error);
            }
        });
    }



function loadAllInventory(){
    $.ajax({
        url: "http://localhost:8080/inventory",
        method: "GET",
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (res) {
            console.log('Response:', res);
            for (var r of res) {
                let itemPicture = r.itemPicture && r.itemPicture.startsWith('data:image/jpeg;base64,')
                    ? r.itemPic
                    : 'path/to/default/image.jpg';
                let pQuantity = r.pQuantity !== undefined ? r.pQuantity : 'N/A';

                let row = `<tr>
            <th>${r.itemCode}</th>
            <td>${r.itemDescription}</td>
            <td><img src="${itemPicture}" alt="${r.itemDescription}'s Picture" style="width: 50px; height: 50px;"/></td>
            <td>${r.category}</td>
            <td>${r.size}</td>
            <td>${r.supplierCode}</td>
            <td>${r.supplierName}</td>
            <td>${r.unitPriceSale}</td>
            <td>${r.unitPriceBuy}</td>
            <td>${r.expectedProfit}</td>
            <td>${r.profitMargin}</td>
            <td>${r.quantity}</td>
            <td>${r.pquantity}</td>
            <td>${r.status}</td>
        </tr>`;
                $("#invnTboady").append(row);
            }
        },
        error: function (xhr, status, error) {
            console.error('Error loading employees:', error);
        }
    });
    btnRowClickInv();

}

function btnRowClickInv() {
    $('#inventoryTable').on('click', 'tr', function() {
        // Your existing code here
        let id=$(this).children(":eq(0)").text();
        let name=$(this).children(":eq(1)").text();
        let gender=$(this).children(":eq(2)").text();
        let joindate=$(this).children(":eq(3)").text();
        let level=$(this).children(":eq(4)").text();
        let totp=$(this).children(":eq(5)").text();
        let dob=$(this).children(":eq(6)").text();
        let ad1=$(this).children(":eq(7)").text();
        let ad2=$(this).children(":eq(8)").text();
        let ad3=$(this).children(":eq(9)").text();
        let ad4=$(this).children(":eq(10)").text();
        let email=$(this).children(":eq(11)").text();
        let st=$(this).children(":eq(12)").text();
        let qnti=$(this).children(":eq(13)").text();
        let pq=$(this).children(":eq(14)").text();



        // console.log(id,name,address,contact);

        $('#itemPic').val(id);
        $('#itemCode').val(name);
        $('#itemDescription').val(gender);
        $('#categoryin').val((joindate));
        $('#size').val(level);
        $('#supplierCodein').val(totp);
        $('#supplierNamein').val((dob));
        $('#unitPriceSale').val(ad1);
        $('#unitPriceBuy').val(ad2);
        $('#expectedProfit').val(ad3);
        $('#landLineNo').val(ad4);
        $('#profitMargin').val(email);
        $('#statusin').val(st);
        $('#quantity').val(qnti);
        $('#pQuantity').val(pq);


    });
}

function addRowToTable() {
    $('#invnTboady').on('click', 'tr', function() {
        // Get the data from the clicked row
        const cells = $(this).children('td');
        $('#itemCode').val(cells.eq(0).text());
        $('#itemDescription').val(cells.eq(1).text());
        // Note: For file inputs, you cannot set a value programmatically
        $('#categoryin').val(cells.eq(3).text());
        $('#size').val(cells.eq(4).text());
        $('#supplierCodein').val(cells.eq(5).text());
        $('#supplierNamein').val(cells.eq(6).text());
        $('#unitPriceSale').val(cells.eq(7).text());
        $('#unitPriceBuy').val(cells.eq(8).text());
        $('#expectedProfit').val(cells.eq(9).text());
        $('#profitMargin').val(cells.eq(10).text());
        $('#quantity').val(cells.eq(11).text());
        $('#pQuantity').val(cells.eq(12).text());
        $('#statusin').val(cells.eq(13).text());
    });
}

$('#deleteinvbtn').click(function (){
    // $('#tbCustomer').empty();
    let itmCode = $("#itemCode").val();
    $.ajax({
        url:"http://localhost:8080/inventory?inventoryCode="+itmCode,
        method:"DELETE",
        headers: {
            'Authorization': 'Bearer ' + token
        },
        // data:data ,
        success:function (res) {
            console.log(res)
            loadAllInventory();

            Swal.fire({
                icon: 'success',
                title: 'Delete Successfully',
                text: res.itemCode
            });


        },
        error:function (ob,status,t){
            console.log(ob);
            console.log(status);
            console.log(t);

        }
    })
});
