
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
    // btnRowClick(); // Assuming this function is defined elsewhere
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


