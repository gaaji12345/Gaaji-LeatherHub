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

$('#supplierSave').click(function() {
    // Create a JSON object from the form data
    var formData = $("#supplierForm").serializeArray();
    var data = {};
    $(formData).each(function(index, obj) {
        data[obj.name] = obj.value;
    });

    console.log(data);
    console.log('Token:', token);

    $.ajax({
        url: 'http://localhost:8080/api/suppliers', // Make sure this matches your controller endpoint
        method: "POST",
        contentType: 'application/json', // Specify content type
        headers: {
            'Authorization': 'Bearer ' + token
        },
        data: JSON.stringify(data), // Convert data to JSON string
        success: function(res) {
            Swal.fire({
                icon: 'success',
                title: 'Saved Successfully',
                text: res
            });

            fetchSuppliers();
        },
        error: function(ob, txtStatus, error) {
            alert(txtStatus);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: ob.responseText // Show the error response text
            });
        }
    });
});



function deleteSupplier(supplierCode) {
    if (confirm('Are you sure you want to delete this supplier?')) {
        $.ajax({
            url: `http://localhost:8080/api/suppliers?id=${supplierCode}`, // Adjust the URL to match your DELETE endpoint
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + token
            },

            success: function() {
                alert('Supplier deleted successfully');
                // fetchSuppliers(); // Refresh the list
            },
            error: function(err) {
                // console.error('Error deleting supplier:', err);
            }
        });
    }
}
