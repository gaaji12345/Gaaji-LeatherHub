
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
            clearFeildsem();
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


function populateEmployeeTable(data) {
    const tableBody = $('#employeeTboady');
    tableBody.empty(); // Clear existing rows

    data.forEach(employee => {
        const row = `<tr data-code="${employee.code}">` + // Store employee code in data attribute
            `<td>${employee.code}</td>` +
            `<td>${employee.employeeName}</td>` +
            `<td>${employee.pic}</td>` + // Adjust as needed
            `<td>${employee.gender}</td>` +
            `<td>${employee.status}</td>` +
            `<td>${employee.designation}</td>` +
            `<td>${employee.role}</td>` +
            `<td>${employee.dob}</td>` +
            `<td>${employee.dateOfJoin}</td>` +
            `<td>${employee.attachedBranch}</td>` +
            `<td>${employee.addressLine01}</td>` +
            `<td>${employee.addressLine02}</td>` +
            `<td>${employee.addressLine03}</td>` +
            `<td>${employee.addressLine04}</td>` +
            `<td>${employee.addressLine05}</td>` +
            `<td>${employee.contactNo}</td>` +
            `<td>${employee.email}</td>` +
            `<td>${employee.emergencyContact}</td>` +
            `<td>${employee.emergencyContactPerson}</td>` +
            `</tr>`;
        tableBody.append(row);
    });
}

$(document).ready(function() {
    // Handle delete button click
    $('#deleteEmployee').on('click', function() {
        // Get the selected employee's row
        const selectedRow = $('#employeeTable tbody tr.selected');

        if (selectedRow.length === 0) {
            alert('Please select an employee to delete.');
            return;
        }

        // Retrieve employeeCode from the data attribute
        const employeeCode = selectedRow.data('code');

        // Confirmation before deletion
        if (confirm('Are you sure you want to delete this employee?')) {
            $.ajax({
                url: `http://localhost:8080/employee?employeeCode=${employeeCode}`, // Update URL with query parameter
                type: 'DELETE',
                headers: {
                    'Authorization': 'Bearer ' + token // Ensure token is defined
                },
                success: function(response) {
                    Swal.fire({
                        icon: 'success',
                        title: 'Delete Successfully',
                        text: response
                    });
                    getAllEmployee();
                    clearFeildsem();
                },
                error: function(xhr, status, error) {

                    Swal.fire({
                        icon: 'error',
                        title: 'Can delete',
                        text: error
                    });
                }
            });
        }
    });

    // Function to handle row selection (add selected class)
    $('#employeeTable tbody').on('click', 'tr', function() {
        $('#employeeTable tbody tr').removeClass('selected'); // Remove 'selected' class from all rows
        $(this).addClass('selected'); // Add 'selected' class to the clicked row
    });

    // Example function to get all employees (optional for refreshing the list)
    function getAllEmployees() {
        $.ajax({
            url: 'http://localhost:8080/employee', // Adjust this URL as per your API
            type: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token // Ensure token is defined
            },
            success: function(data) {
                populateEmployeeTable(data); // Call the function to populate the table
            },
            error: function(xhr, status, error) {
                alert('Error fetching employees: ' + error);
            }
        });
    }
});


$(document).ready(function() {
    $('#employeeUpdate').on('click', function() {
        const selectedRow = $('#employeeTable tbody tr.selected');

        if (selectedRow.length === 0) {
            alert('Please select an employee to update.');
            return;
        }

        const employeeCode = selectedRow.data('employeeCode');

        const formData = new FormData();
        const fileInput = $('#file')[0].files[0];
        if (!fileInput) {
            alert('Please select a file to upload.');
            return;
        }
        formData.append('file', fileInput);

        // Append each property separately
        formData.append('employeeName', $('#employeeName').val());
        formData.append('gender', $('#genderem').val());
        formData.append('status', $('#status').val());
        formData.append('designation', $('#designation').val());
        formData.append('role', $('#role').val());
        formData.append('dob', $('#dobem').val());
        formData.append('dateOfJoin', $('#dateOfJoin').val());
        formData.append('attachedBranch', $('#attachedBranch').val());
        formData.append('addressLine01', $('#addressLine01').val());
        formData.append('addressLine02', $('#addressLine02').val());
        formData.append('addressLine03', $('#addressLine03').val());
        formData.append('addressLine04', $('#addressLine04').val());
        formData.append('addressLine05', $('#addressLine05').val());
        formData.append('contactNo', $('#contactNoem').val());
        formData.append('email', $('#emailem').val());
        formData.append('emergencyContact', $('#emergencyContact').val());
        formData.append('emergencyContactPerson', $('#emergencyContactPerson').val());

        $.ajax({
            url: `http://localhost:8080/employee/${employeeCode}`,
            type: 'PUT',
            data: formData,
            processData: false,
            contentType: false,
            headers: {
                'Authorization': 'Bearer ' + token // Ensure token is defined
            },
            success: function(response) {
                alert('Employee updated successfully!');
                // Optionally refresh the employee table
                // getAllEmployees(); // Uncomment if needed
            },
            error: function(xhr, status, error) {
                // console.error('Error details:', xhr.responseText);
                // alert('Error updating employee: ' + xhr.status + ' - ' + error);
            }
        });
    });
});


function clearFeildsem() {
    $("#file,#employeeName,#genderem,#status,#designation,#role,#dobem,#dateOfJoin,#attachedBranch,#addressLine01,#addressLine02,#addressLine03,#addressLine04,#addressLine05,#contactNoem,#emailem,#emergencyContact,#emergencyContactPerson").val("");
    $('#file').focus();
}















