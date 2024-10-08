
var token = localStorage.getItem('token');

$(document).ready(function() {

    getAllCustomers();


});


getNextCustomerCode();
btnRowClick();

$('#level').change(function(){
    console.log($(this).val());
    switch($(this).val()) {
        case 'GOLD':
            $('#totalPoints').val('800');
            break;
        case 'SILVER':
            $('#totalPoints').val('600');
            break;
        case 'BRONZE':
            $('#totalPoints').val('400');
            break;
        case 'LEGEND':
            $('#totalPoints').val('2000');
            break;
        default:
            alert('No valid level selected');
    }
});
$('#customergetAll').click(function(){
    getAllCustomers();
})
function getAllCustomers() {


    $("#customerTboady").empty();
    $.ajax({
        url: "http://localhost:8080/customer",
        method: "GET",
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (res) {
            console.log(res);
            for (var r of res.data) {
                let row = `<tr>
                    <th>${r.customerCode}</th>
                    <td>${r.customerName}</td>
                    <td>${r.gender}</td>
                    <td>${r.joinDate}</td>
                    <td>${r.level}</td>
                    <td>${r.totalPoints}</td>
                    <td>${r.dob}</td>
                    <td>${r.address_line_01}</td>
                    <td>${r.address_line_02}</td>
                    <td>${r.address_line_03}</td>
                    <td>${r.address_line_04}</td>
                    <td>${r.address_line_05}</td>
                    <td>${r.contactNo}</td>
                    <td>${r.email}</td>
                    <td>${r.recentPurchaseDateTime}</td>
                    </tr>`;
                $("#customerTboady").append(row);

            }
        }

    });
    btnRowClick();
}

function getNextCustomerCode(){
    $.ajax({
        url:'http://localhost:8080/customer/nextId',
        method:'GET',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + token
        },

        success: function(resp){
            console.log(resp);
            $('#customerCode').val(resp)
        }
    });
}

$('#customerSave').click(function() {
    // Create a JSON object from the form data
    var formData = $("#customerForm").serializeArray();
    var data = {};
    $(formData).each(function(index, obj) {
        data[obj.name] = obj.value;
    });

    console.log(data);
    console.log('Token:', token);

    $.ajax({
        url: 'http://localhost:8080/customer', // Make sure this matches your controller endpoint
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
            getAllCustomers();
            clearFeilds();
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



function btnRowClick() {
    $('#customerTable').on('click', 'tr', function() {
        // Your existing code here
        let id=$(this).children(":eq(0)").text();
        let name=$(this).children(":eq(1)").text();
        let gender=$(this).children(":eq(2)").text();
        let joindate=$(this).children(":eq(3)").text();
        let level=$(this).children(":eq(4)").text();
        let totp=$(this).children(":eq(5)").text();
        let dob=$(this).children(":eq(6)").text();
        let ad1=$(this).children(":eq(7)").text();
        let ad2=$(this).children(":eq(8)").text();
        let ad3=$(this).children(":eq(9)").text();
        let ad4=$(this).children(":eq(10)").text();
        let ad5=$(this).children(":eq(11)").text();
        let nmb=$(this).children(":eq(12)").text();
        let email=$(this).children(":eq(13)").text();
        let pdate=$(this).children(":eq(14)").text();


        // console.log(id,name,address,contact);

        $('#customerCode').val(id);
        $('#customerName').val(name);
        $('#gender').val(gender);
        $('#joinDate').val(extractDate(joindate));
        $('#level').val(level);
        $('#totalPoints').val(totp);
        $('#dob').val(extractDate(dob));
        $('#address_line_01').val(ad1);
        $('#address_line_02').val(ad2);
        $('#address_line_03').val(ad3);
        $('#address_line_04').val(ad4);
        $('#address_line_05').val(ad5);
        $('#contactNo').val(nmb);
        $('#email').val(email);
        $('#recent_purchase_date_time').val(extractDate(pdate));
    });
}


function extractDate(dateStr) {
    // Check if the input is in the correct format
    if (dateStr) {
        // Split at "T" and take the first part
        return dateStr.split('T')[0];
    }
    return ''; // Return an empty string if dateStr is undefined or empty
}

$('#deleteCustomer').click(function (){
    // $('#tbCustomer').empty();
    let customerID = $("#customerCode").val();
    $.ajax({
        url:"http://localhost:8080/customer?customerCode="+customerID,
        method:"DELETE",
        headers: {
            'Authorization': 'Bearer ' + token
        },
        // data:data ,
        success:function (res) {
            console.log(res)
        getAllCustomers();
            clearFeilds();

                Swal.fire({
                    icon: 'success',
                    title: 'Delete Successfully',
                    text: res
                });


        },
        error:function (ob,status,t){
            console.log(ob);
            console.log(status);
            console.log(t);

        }
    })
});


$('#customerUpdate').click(function (){
    updateCustomer();
})

function updateCustomer(){
    var cusOb={
        customerCode: $('#customerCode').val(),
        customerName:$('#customerName').val(),
         gender: $('#gender').val(),
        joinDate:$('#joinDate').val(),
        level:$('#level').val(),
       totalPoints: $('#totalPoints').val(),
       dob: $('#dob').val(),
        address_line_01 : $('#address_line_01').val(),
        address_line_02:  $('#address_line_02').val(),
        address_line_03: $('#address_line_03').val(),
        address_line_04: $('#address_line_04').val(),
        address_line_05: $('#address_line_05').val(),
        contactNo: $('#contactNo').val(),
       email: $('#email').val(),
        recent_purchase_date_time: $('#recent_purchase_date_time').val()

    }

    $.ajax({
        url:'http://localhost:8080/customer',
        method:"PUT",
        contentType:"application/json",//request contetnt type json
        headers: {
            'Authorization': 'Bearer ' + token
        },

        data:JSON.stringify(cusOb),
        success:function (res){

         getAllCustomers();
            Swal.fire({
                icon: 'success',
                title: 'Update Successfully',
                text: res
            });
            clearFeilds();

        },
        error:function (ob,txtStatus,error){
            alert(txtStatus);
            console.log(ob.responseText)
        }
    })

}


function clearFeilds() {
    $("#customerCode,#customerName,#gender,#joinDate,#level,#totalPoints,#dob,#address_line_01,#address_line_02,#address_line_03,#address_line_04,#address_line_05,#contactNo,#email,#recent_purchase_date_time").val("");
    getNextCustomerCode();
    $('#customerCode').focus();
}
