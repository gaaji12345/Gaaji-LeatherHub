
//  $(document).ready(function() {
//   login();
//  });

$('#dashboard').hide();
$('#userdashboard').hide();

$('#registerLink').click(function (){
    $('#login').hide(); // Hide login form-->
    $('#register').show();
    $('.seclog').css('display','none');

});

$('#backButton').click(function (){
    $('.seclog').show(); // Hide login form-->
    $('#login').show();
    $('#register').hide();
});
//
// $(document).ready(function() {
//     $("#btnlog").click(function() {
//         const loginRequest = {
//             username: $("#loginEmail").val(),
//             password: $("#loginPassword").val()
//         };
//
//         $.ajax({
//             url: 'http://localhost:8080/auth/login',
//             type: 'POST',
//             contentType: 'application/json',
//             data: JSON.stringify(loginRequest),
//             success: function(data) {
//                 console.log('Login successful:', data);
//
//                 // Check the user's role and navigate accordingly
//                 if (data.role === 'ADMIN') {
//                     // window.location.href = '/admin/dashboard'; // Navigate to admin dashboard
//                     $("#dashboard").show();
//                 } else {
//                     window.location.href = '/user/dashboard'; // Navigate to user dashboard
//                 }
//             },
//             error: function(jqXHR) {
//                 console.error('Login failed:', jqXHR);
//                 // Handle error (e.g., show an error message)
//                 alert('Login failed: ' + jqXHR.responseJSON.message);
//             }
//         });
//     });
// });
















$(document).ready(function() {
    $('#loginForm').on('submit', function(event) {
        event.preventDefault(); // Prevent default form submission

        const email = $('#loginEmail').val();
        const password = $('#loginPassword').val();


        if (!email || !password) {
            showError("Email and Password are required");
            return;
        }

        login(email, password)
            .then(response => {
                if (response.role === 'ADMIN') {
                    localStorage.setItem('token', response.token);
                    localStorage.setItem('role', response.role);
                    $('#dashboard').show();
                    $('#mainlogin').hide();
                    $('.seclog').hide();
                    Swal.fire({
                        icon: 'success',
                        title: 'Login Successful',
                        text: response
                    });
                }   else {
                    $('#userdashboard').show();
                    $('#mainlogin').hide();
                    $('.seclog').hide();
                    Swal.fire({
                        icon: 'success',
                        title: 'Login Successful',
                        text: response
                    });
                }
            })
            .catch(error => {
                showError(error.message);
            });

        function showError(message) {

            setTimeout(() => {

            }, 3000);
        }

        function login(email, password) {
            return new Promise((resolve, reject) => {
                $.ajax({
                    url: 'http://localhost:8080/auth/login', // Your login endpoint
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({ email, password }),
                    success: function(response) {
                        resolve(response);
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        reject(new Error(errorThrown || textStatus));
                    }
                });
            });
        }
    });
});










$('#registerForms').on('submit', function(event) {
    event.preventDefault();
    const registrationRequestss = {
        email: $("#registerEmail").val(),
        name: $("#registerName").val(),
        phoneNumber: $("#registerphoneNumber").val(),
        password: $("#registerPassword").val(),
        role: $("#userRole").val()
    };


    // if (password !== confirmPassword) {
    //     Swal.fire({
    //         icon: 'error',
    //         title: 'Registration Failed',
    //         text: 'Passwords do not match.'
    //     });
    //     return;
    // }

    $.ajax({
        url: 'http://localhost:8080/auth/register',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(registrationRequestss),
        success: function(response) {
            Swal.fire({
                icon: 'success',
                title: 'Registration Successful',
                text: response
            });
        },
        error: function() {
            Swal.fire({
                icon: 'error',
                title: 'Registration Failed',
                text: 'Error while registering user.'
            });
        }
    });
});





// For the Registration Form
// $('#registerForm').on('submit', function(event) {
//     event.preventDefault();
//     const email = $('#registerEmail').val();
//     const password = $('#registerPassword').val();
//     const confirmPassword = $('#confirmPassword').val();
//     const role = $('#userRole').val();
//
//     if (password !== confirmPassword) {
//         Swal.fire({
//             icon: 'error',
//             title: 'Registration Failed',
//             text: 'Passwords do not match.'
//         });
//         return;
//     }
//
//     $.ajax({
//         url: 'http://localhost:8080/api/v1/auth/signup',
//         type: 'POST',
//         contentType: 'application/json',
//         data: JSON.stringify({ email: email, password: password, role: role }),
//         success: function(response) {
//             Swal.fire({
//                 icon: 'success',
//                 title: 'Registration Successful',
//                 text: response
//             });
//         },
//         error: function() {
//             Swal.fire({
//                 icon: 'error',
//                 title: 'Registration Failed',
//                 text: 'Error while registering user.'
//             });
//         }
//     });
// });


