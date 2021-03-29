$(document).ready(function () {
    $("#id_btn").bind("click", function () {
        likeToDetail();
    });

    $('#id_btn').trigger("click");
});


function likeToDetail(postId) {
    $("#id_btn").unbind('click');

    console.log("post아이디는" + postId);

    $.ajax({
        url: "/like/getlike/" + Number(postId),
        type: "GET",
        dataType: "json"
    }).done(function (msg) {
        console.log(msg["Message"]);

        if(msg["Message"] == "already clicked") {
            $('#createLike').css('background-color', 'rgb(255, 255, 0)');
        }
    });
};

