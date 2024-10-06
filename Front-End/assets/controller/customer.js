
var token = localStorage.getItem('token');

getNextCustomerCode();
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


    // $("#customerTable").empty();
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
