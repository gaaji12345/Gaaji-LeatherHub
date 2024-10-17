
var token = localStorage.getItem('token');

$(document).ready(function() {

    loadAllInventory();


});


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

}
