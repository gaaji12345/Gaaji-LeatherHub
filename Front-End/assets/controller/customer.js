
var token = localStorage.getItem('token');



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
