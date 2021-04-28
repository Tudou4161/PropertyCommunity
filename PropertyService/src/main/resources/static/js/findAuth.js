$("#findIDbutton").bind('click', function () {
    findID();
});

var findID = function (){
    //$("#findIDbutton").unbind('click');

    var name = document.getElementById("name1").value;
    var phone_num = document.getElementById("phonenumb").value;

    if (name == "" || name == " " || phone_num == "" || phone_num == " ") {
        alert("둘다 입력해주세요!");
        return ;
    }

    $.ajax({
        type: "GET",
        url: '/findUser/findEmail/' + name + '/' + phone_num,
        dataType: "json"
    }).done(function (data) {
        console.log(data.message);
        alert(data.message);
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
};