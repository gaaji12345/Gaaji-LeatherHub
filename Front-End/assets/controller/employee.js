
var token = localStorage.getItem('token');


$(document).ready(function() {

   getAllEmployee();


});

function getAllEmployee() {
    $("#employeeTboady").empty(); // Clear the existing table body
    $.ajax({
        url: "http://localhost:8080/employee",
        method: "GET",
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (res) {
            console.log(res);
            for (var r of res) {
                let empPic = r.empPic ? `data:image/jpeg;base64,${r.empPic}` : 'path/to/default/image.jpg'; // Use a default image if empPic is empty
                let row = `<tr>
                    <th>${r.employeeCode}</th>
                    <td>${r.employeeName}</td>
                    <td><img src="${empPic}" alt="${r.employeeName}'s Picture" style="width: 50px; height: 50px;"/></td>
                    <td>${r.gender}</td>
                    <td>${r.status}</td>
                    <td>${r.designation}</td>
                    <td>${r.role}</td>
                    <td>${new Date(r.dob).toLocaleDateString()}</td>
                    <td>${new Date(r.dateOfJoin).toLocaleDateString()}</td>
                    <td>${r.attachedBranch}</td>
                    <td>${r.addressLine01}</td>
                    <td>${r.addressLine02}</td>
                    <td>${r.addressLine03}</td>
                    <td>${r.addressLine04}</td>
                    <td>${r.addressLine05}</td>
                    <td>${r.contactNo}</td>
                    <td>${r.email}</td>
                    <td>${r.emergencyContact}</td>
                    <td>${r.emergencyContactPerson}</td>
                </tr>`;
                $("#employeeTboady").append(row);
            }
        },
        error: function (xhr, status, error) {
            console.error('Error loading employees:', error);
        }
    });
     btnRowClickem(); // Assuming this function is defined elsewhere
}

$('#employeeForm').on('submit', function(e) {
    e.preventDefault(); // Prevent default form submission

    var formData = new FormData(this);

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/employee', // Adjust the URL as per your API
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
            getAllEmployee();
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

function btnRowClickem() {
    $('#employeeTable').on('click', 'tr', function() {
        var cells = $(this).children('td');

        // Assuming the columns are in the same order as your form fields
        $('#employeeName').val(cells.eq(0).text()); // Name
        $('#genderem').val(cells.eq(2).text()); // Gender
        $('#status').val(cells.eq(3).text()); // Status
        $('#designation').val(cells.eq(4).text()); // Designation
        $('#role').val(cells.eq(5).text()); // Role
        $('#dobem').val(convertDateFormat(cells.eq(6).text())); // DOB
        $('#dateOfJoin').val(convertDateFormat(cells.eq(7).text())); // Date of Joining
        $('#attachedBranch').val(cells.eq(8).text()); // Attached Branch
        $('#addressLine01').val(cells.eq(9).text()); // Address Line 1
        $('#addressLine02').val(cells.eq(10).text()); // Address Line 2
        $('#addressLine03').val(cells.eq(11).text()); // Address Line 3
        $('#addressLine04').val(cells.eq(12).text()); // Address Line 4
        $('#addressLine05').val(cells.eq(13).text()); // Address Line 5
        $('#contactNoem').val(cells.eq(14).text()); // Contact No
        $('#emailem').val(cells.eq(15).text()); // Email
        $('#emergencyContact').val(cells.eq(16).text()); // Emergency Contact
        $('#emergencyContactPerson').val(cells.eq(17).text()); // Emergency Contact Person

        // Optionally show the form if it’s hidden
        $('#mainEmployee').show();
    });
}

function convertDateFormat(dateStr) {
    // Check if the input is in dd/MM/yyyy format
    if (dateStr) {
        const parts = dateStr.split('/');
        if (parts.length === 3) {
            const day = parts[0];
            const month = String(parts[1]).padStart(2, '0'); // Ensure two digits
            const year = parts[2];

            // Return in yyyy-MM-dd format
            return `${year}-${month}-${day}`;
        }
    }
    return ''; // Return an empty string if dateStr is undefined or empty
}











