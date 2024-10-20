var token = localStorage.getItem('token');

$(document).ready(function() {

  getAllSuppliers();
  getNextSuptomerCode();


});

function getNextSuptomerCode(){
    $.ajax({
        url:'http://localhost:8080/api/suppliers/code',
        method:'GET',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + token
        },

        success: function(resp){
            console.log(resp);
            $('#supplierCode').val(resp)
        }
    });
}

function getAllSuppliers() {


    $("#supplierTableBody").empty();
    $.ajax({
        url: "http://localhost:8080/api/suppliers",
        method: "GET",
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (res) {
            console.log(res);
            for (var supplier of res) {
                let row = `<tr>
                      <th>${supplier.supplierCode}</th>
                            <td>${supplier.supplierName}</td>
                            <td>${supplier.category}</td>
                            <td>${supplier.addressLine01}</td>
                            <td>${supplier.addressLine02 }</td>
                            <td>${supplier.addressLine03 }</td>
                            <td>${supplier.addressLine04 }</td>
                            <td>${supplier.addressLine05 }</td>
                            <td>${supplier.addressLine06 }</td>
                            <td>${supplier.contactNo1}</td>
                            <td>${supplier.landLineNo }</td>
                            <td>${supplier.email}</td>
                    </tr>`;
                $("#supplierTableBody").append(row);

            }
        }

    });
    btnRowClickSup();
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

            getAllSuppliers();
            clearFeildsSuppliers();
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

$('#deletesupplier').click(function (){
    deleteSupplier();
})


function  deleteSupplier(){
    let supId = $("#supplierCode").val();
    $.ajax({
        url:"http://localhost:8080/api/suppliers?id="+supId,
        method:"DELETE",
        headers: {
            'Authorization': 'Bearer ' + token
        },
        // data:data ,
        success:function (res) {
            console.log(res)
           getAllSuppliers();
            clearFeildsSuppliers();

            Swal.fire({
                icon: 'success',
                title: 'Delete Successfully',
                text: res.supplierCode
            });


        },
        error:function (ob,status,t){
            console.log(ob);
            console.log(status);
            console.log(t);

        }
    })
}



$('#updatesuuplier').click(function () {
    var formData = $("#supplierForm").serializeArray();
    var data = {};
    $(formData).each(function (index, obj) {
        data[obj.name] = obj.value;
    });

    console.log(data);
    console.log('Token:', token);

    $.ajax({
        url: 'http://localhost:8080/api/suppliers', // Ensure this is the correct update endpoint
        method: "PUT",
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        data: JSON.stringify(data), // Convert data to JSON string
        success: function (res) {
            Swal.fire({
                icon: 'success',
                title: 'Updated Successfully',
                text: res
            });


            getAllSuppliers(); // Refresh the suppliers list
            clearFeildsSuppliers();
            // Clear the form fields after update
        },
        error: function (ob, txtStatus, error) {
            alert(txtStatus);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: ob.responseText // Show the error response text
            });
        }
    });
})



function btnRowClickSup() {
    $('#supTable').on('click', 'tr', function() {
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
        let email=$(this).children(":eq(11)").text();



        // console.log(id,name,address,contact);

        $('#supplierCode').val(id);
        $('#supplierName').val(name);
        $('#category').val(gender);
        $('#addressLine01sup').val((joindate));
        $('#addressLine02sup').val(level);
        $('#addressLine03sup').val(totp);
        $('#addressLine04suo').val((dob));
        $('#addressLine05sup').val(ad1);
        $('#addressLine06').val(ad2);
        $('#contactNo1').val(ad3);
        $('#landLineNo').val(ad4);
        $('#emailsup').val(email);

    });
}

function clearFeildsSuppliers() {
    $("#supplierCode,#supplierName,#category,#addressLine01sup,#addressLine02sup,#addressLine03sup,#addressLine04suo,#addressLine05sup,#addressLine06,#contactNo1,#landLineNo,#emailsup").val("");
    getNextSuptomerCode();
    $('#supplierCode').focus();
}
