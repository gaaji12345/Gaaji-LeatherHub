
var token = localStorage.getItem('token');

$(document).ready(function() {

  fetchAllUsers();


});



$('#userSave').click(function() {
    // Create a JSON object from the form data
    var formData = $("#registrationForms2").serializeArray();
    var data = {};
    $(formData).each(function(index, obj) {
        data[obj.name] = obj.value;
    });

    console.log(data);
    console.log('Token:', token);

    $.ajax({
        url: 'http://localhost:8080/auth/register', // Make sure this matches your controller endpoint
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
          fetchAllUsers();
          clearInputFields();
            // clearFeilds();
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
function fetchAllUsers() {
    $.ajax({
        url: 'http://localhost:8080/auth', // Adjust URL as needed
        type: 'GET',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + token
        },

        success: function(response) {
            const userTableBody = $('#userTable tbody');
            userTableBody.empty(); // Clear previous entries

            response.userList.forEach(user => {
                userTableBody.append(`
                            <tr>
                                <td>${user.email}</td>
                                <td>${user.name}</td>
                                <td>${user.phoneNumber}</td>
                                <td>${user.role}</td>
                                <td><button class="btn btn-danger btn-sm deleteBtn" data-email="${user.email}">Delete</button></td>
                            </tr>
                        `);
            });
        },
        error: function() {
            alert('Error fetching user data.');
        }
    });
    btnRowClickuser();
}

// Populate input fields when a row is clicked
function btnRowClickuser() {
    $('#userTable').on('click', 'tr', function() {
        // Your existing code here
        let id=$(this).children(":eq(0)").text();
        let name=$(this).children(":eq(1)").text();
        let nmb=$(this).children(":eq(2)").text();
        let pw=$(this).children(":eq(3)").text();



        // console.log(id,name,address,contact);

        $('#emailuser').val(id);
        $('#name').val(name);
        $('#phoneNumber').val(nmb);
        $('#password').val('');

    });
}


function remove() {
    $("#userTable tbody").on("dblclick", "tr", function () {
        // Ask for confirmation before removing the user
        const confirmed = confirm("Are you sure you want to delete this user?");

        if (confirmed) {
            // Remove the row from the table
            $(this).remove();
            Swal.fire({
                icon: 'success',
                title: 'Removed Successfully',
                text: ''
            });

            // Show success alert

        }else {
            Swal.fire({
                icon: 'success',
                title: 'Try again',
                text: ''
            });
        }
    });
}

// Call the remove function to attach the event listener
remove();



// Initial fetch of users
function clearInputFields() {
    $('#emailuser').val('');
    $('#name').val('');
    $('#phoneNumber').val('');
    $('#password').val('');
    $('#roleuser').val('user'); // Set default role
}

