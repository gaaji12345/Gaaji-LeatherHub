
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





// $(document).ready(function() {
//     $('#signupForm').on('submit', function(event) {
//         event.preventDefault(); // Prevent default form submission
//
//         const email = $('#signupEmail').val();
//         const password = $('#signupPassword').val();
//
//         // Validate inputs
//         if (!email || !password) {
//             showError("Email and Password are required");
//             return;
//         }
//
//         // Show loading indicator
//         Swal.fire({
//             title: 'Signing up...',
//             onBeforeOpen: () => {
//                 Swal.showLoading();
//             }
//         });
//
//         // Call the sign-up function
//         signUp(email, password)
//             .then(response => {
//                 Swal.close(); // Close loading indicator
//
//                 // Check if the response indicates success
//                 if (response.success) {
//                     Swal.fire({
//                         icon: 'success',
//                         title: 'Sign Up Successful',
//                         text: response.message || 'Welcome!'
//                     });
//                     // Optionally redirect or reset the form
//                     $('#signupForm')[0].reset();
//                 } else {
//                     showError(response.message || "Sign up failed. Please try again.");
//                 }
//             })
//             .catch(error => {
//                 Swal.close(); // Close loading indicator
//                 showError(error.message);
//             });
//
//         // Function to show error messages
//         function showError(message) {
//             Swal.fire({
//                 icon: 'error',
//                 title: 'Error',
//                 text: message,
//                 timer: 3000 // Automatically closes after 3 seconds
//             });
//         }
//
//         // Function to handle sign-up via AJAX
//         function signUp(email, password) {
//             return new Promise((resolve, reject) => {
//                 $.ajax({
//                     url: 'http://localhost:8080/api/v1/auth/signup', // Your sign-up endpoint
//                     type: 'POST',
//                     contentType: 'application/json',
//                     data: JSON.stringify({ email, password }),
//                     success: function(response) {
//                         resolve(response);
//                     },
//                     error: function(jqXHR, textStatus, errorThrown) {
//                         reject(new Error(errorThrown || textStatus));
//                     }
//                 });
//             });
//         }
//     });
// });


// $(document).ready(function() {
//     $('#loginForm').on('submit', function(event) {
//         event.preventDefault(); // Prevent default form submission
//
//         const email = $('#loginEmail').val();
//         const password = $('#loginPassword').val();
//
//         // Validate inputs
//         if (!email || !password) {
//             showError("Email and Password are required");
//             return;
//         }
//
//         // Simple email validation
//         const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
//         if (!emailPattern.test(email)) {
//             showError("Please enter a valid email address");
//             return;
//         }
//
//         // Show loading indicator
//         Swal.fire({
//             title: 'Logging in...',
//             onBeforeOpen: () => {
//                 Swal.showLoading();
//             }
//         });
//
//         // Call the login function
//         login(email, password)
//             .then(response => {
//                 Swal.close(); // Close loading indicator
//                 console.log("Response:", response); // Log the entire response
//
//                 // Check if the response indicates success
//                 if (response.status === 'Created') {
//                     const authData = response.data; // Access the AuthDTO
//                     const token = authData.token;
//                     const role = authData.role; // Assuming you have the role in AuthDTO
//
//                     // Store token in localStorage
//                     localStorage.setItem('token', token);
//                     localStorage.setItem('role', role);
//
//                     // Navigate to the appropriate dashboard
//                     if (role === 'DASH_ADMIN') {
//                         // window.location.href = '/admin/dashboard'; // Redirect to admin dashboard
//                         $("#dashboard").show();
//                     } else {
//                         window.location.href = '/user/dashboard'; // Redirect to user dashboard
//                     }
//
//                     // Optionally show a success message
//                     Swal.fire({
//                         icon: 'success',
//                         title: 'Login Successful',
//                         text: response.message || 'Welcome!'
//                     });
//                 } else {
//                     // Handle unsuccessful login (wrong email/password)
//                     showError(response.message || "Login failed. Please check your credentials.");
//                 }
//             })
//             .catch(error => {
//                 Swal.close(); // Close loading indicator
//                 console.log("Error:", error); // Log the error
//                 // Handle error from the login attempt
//                 showError(error.message);
//             });
//
//         // Function to show error messages
//         function showError(message) {
//             Swal.fire({
//                 icon: 'error',
//                 title: 'Error',
//                 text: message,
//                 timer: 3000 // Automatically closes after 3 seconds
//             });
//         }
//
//         // Function to handle login via AJAX
//         function login(email, password) {
//             return new Promise((resolve, reject) => {
//                 $.ajax({
//                     url: 'http://localhost:8080/api/v1/auth/authenticate', // Your login endpoint
//                     type: 'POST',
//                     contentType: 'application/json',
//                     data: JSON.stringify({ email, password }),
//                     success: function(response) {
//                         resolve(response);
//                     },
//                     error: function(jqXHR, textStatus, errorThrown) {
//                         console.error(jqXHR.responseText); // Log the error response
//                         reject(new Error(errorThrown || textStatus));
//                     }
//                 });
//             });
//         }
//     });
// });








//
// $(document).ready(function() {
//     $('#loginForm').on('submit', function(event) {
//         event.preventDefault(); // Prevent default form submission
//
//         const email = $('#loginEmail').val();
//         const password = $('#loginPassword').val();
//
//         if (!email || !password) {
//             showError("Email and Password are required");
//             return;
//         }
//
//         login(email, password)
//             .then(response => {
//                 if (response.success) { // Assuming the API response has a 'success' field
//                     if (response.role === 'ADMIN') {
//                         localStorage.setItem('token', response.token);
//                         localStorage.setItem('role', response.role);
//                         $('#dashboard').show();
//                         $('#mainlogin').hide();
//                         $('.seclog').hide();
//                     } else {
//                         $('#userdashboard').show();
//                         $('#mainlogin').hide();
//                         $('.seclog').hide();
//                     }
//                     Swal.fire({
//                         icon: 'success',
//                         title: 'Login Successful',
//                         text: response.message // Change to the appropriate field
//                     });
//                 } else {
//                     showError(response.message || "Login failed. Please check your credentials.");
//                 }
//             })
//             .catch(error => {
//                 showError(error.message);
//             });
//
//         function showError(message) {
//             Swal.fire({
//                 icon: 'error',
//                 title: 'Error',
//                 text: message,
//                 timer: 3000 // Automatically closes after 3 seconds
//             });
//         }
//
//         function login(email, password) {
//             return new Promise((resolve, reject) => {
//                 $.ajax({
//                     url: 'http://localhost:8032/auth/login', // Your login endpoint
//                     type: 'POST',
//                     contentType: 'application/json',
//                     data: JSON.stringify({ email, password }),
//                     success: function(response) {
//                         resolve(response);
//                     },
//                     error: function(jqXHR, textStatus, errorThrown) {
//                         reject(new Error(errorThrown || textStatus));
//                     }
//                 });
//             });
//         }
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

//
// $('#btnlog').on('click', function(event) {
//     event.preventDefault();
//     const email = $('#loginEmail').val();
//     const password = $('#loginPassword').val();
//
//     $.ajax({
//         url: 'http://localhost:8080/auth/login',
//         type: 'POST',
//         contentType: 'application/json',
//         data: JSON.stringify({ email: email, password: password }),
//         success: function(response) {
//             console.log('Response received:', response); // Debugging line
//
//             if (response.success) {
//                 localStorage.setItem('token', response.token);
//                 console.log('Token stored:', response.token); // Confirm token storage
//
//                 // Check user role
//                 console.log('User role:', response.role); // Log the user role
//
//                 // Navigate based on user role
//                 switch (response.role) {
//                     case 'ADMIN':
//                         console.log('Navigating to admin dashboard...');
//                         $('#dashboard').show(); // Show admin dashboard
//                         break;
//                     case 'USER':
//                         console.log('Navigating to user dashboard...');
//                         $('#userdashboard').show(); // Show user dashboard
//                         break;
//                     default:
//                         console.error('Unknown user role:', response.role);
//                 }
//             } else {
//                 // $('#errorMessage').text(response.message || 'Login failed. Please try again.');
//             }
//         },
//         error: function() {
//             Swal.fire({
//                 icon: 'error',
//                 title: 'Login Failed',
//                 text: 'Invalid credentials.'
//             });
//         }
//     });
// });





// $(document).ready(function() {
//     $("#registrationForms").submit(function(event) {
//         event.preventDefault(); // Prevent the form from submitting normally
//
//         const registrationRequests = {
//             email: $("#registerEmail").val(),
//             name: $("#registerName").val(),
//             phoneNumber: $("#registerphoneNumber").val(),
//             password: $("#registerPassword").val(),
//             role: $("#userRole").val()
//         };
//
//         $.ajax({
//             url: 'http://localhost:8080/auth/register',
//             type: 'POST',
//             contentType: 'application/json',
//             data: JSON.stringify(registrationRequests),
//             success: function(response) {
//                 console.log('Registration successful:', response);
//                 alert('Registration successful! Please log in.');
//                 // window.location.href = '/login'; // Redirect to login page
//             },
//             error: function(jqXHR) {
//                 console.error('Registration failed:', jqXHR);
//                 alert('Registration failed: ' + jqXHR.responseJSON.message);
//             }
//         });
//     });
// });


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


