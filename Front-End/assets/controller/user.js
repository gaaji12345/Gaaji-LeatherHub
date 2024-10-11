
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
}





// Initial fetch of users


