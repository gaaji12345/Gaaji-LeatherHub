var token = localStorage.getItem('token');

$(document).ready(function() {

  fetchSuppliers();


});

function fetchSuppliers() {

    $.ajax({
        url: 'http://localhost:8080/api/suppliers', // Change to your actual endpoint
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function(data) {
            // Clear the table body
            $('#supplierTableBody').empty();

            // Populate the table with supplier data
            data.forEach(function(supplier) {
                $('#supplierTableBody').append(`
                        <tr>
                            <td>${supplier.supplierCode}</td>
                            <td>${supplier.supplierName}</td>
                            <td>${supplier.category}</td>
                            <td>${supplier.addressLine01}</td>
                            <td>${supplier.addressLine02 || ''}</td>
                            <td>${supplier.addressLine03 || ''}</td>
                            <td>${supplier.addressLine04 || ''}</td>
                            <td>${supplier.addressLine05 || ''}</td>
                            <td>${supplier.addressLine06 || ''}</td>
                            <td>${supplier.contactNo1}</td>
                            <td>${supplier.landLineNo || ''}</td>
                            <td>${supplier.email}</td>
                            <td>
                                <button class="btn btn-warning btn-sm" onclick="editSupplier('${supplier.supplierCode}')">Edit</button>
                                <button class="btn btn-danger btn-sm" onclick="deleteSupplier('${supplier.supplierCode}')">Delete</button>
                            </td>
                        </tr>
                    `);
            });
        },
        error: function(err) {
            console.error('Error fetching suppliers:', err);
        }
    });
}
