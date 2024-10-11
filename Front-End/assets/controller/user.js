
var token = localStorage.getItem('token');

$(document).ready(function() {

  fetchAllUsers();


});

// Fetch All Users AJAX
// function fetchAllUsers() {
//
//
//     // $('#tbItem').empty();
//     $.ajax({
//         url: 'http://localhost:8080/auth',
//         method: "GET",
//         headers: {
//             'Authorization': 'Bearer ' + token
//         },
//         dataType: "json",//please convert the response into jason
//         success: function (resp) {
//             for (const user of resp) {
//                 // $("#tbjson").empty();
//                 console.log(typeof resp);
//                 let row = `<tr><td>${user.email}</td><td>${user.name}</td><td>${user.phoneNumber}</td><td>${user.password}</td></tr>`
//                 $("#userTboady").append(row);
//             }
//             // rowBack();
//         }
//     })
// }

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


