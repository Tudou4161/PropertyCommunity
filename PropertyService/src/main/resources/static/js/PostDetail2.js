$(document).ready(function () {
    $("#createLike").bind('click', function () {
        createLike();
    });
});

var createLike = function (){

    $("#createLike").unbind('click');

    console.log($('#createLike').css('background-color'));

    if ( $('#createLike').css('background-color') == 'rgb(255, 255, 0)') {
        var postId1 = $("#createLike").attr("value");

        $.ajax({
            type: "GET",
            url: "/likeDelete/" + Number(postId1),
            dataType: "json"
        }).done(function (data) {
            console.log(data);
            $("#createLike").css('background-color', 'rgb(255, 255, 255)');
            $("#likeCnt").text(data["like_cnt"]);
        }).fail(function (request, status, error) {
            console.log(request.status + " : " +
                request.responseText + " : " + error);
        })
    } else {
        var postId2 = $("#createLike").attr("value");

        $.ajax({
            type: "GET",
            url: "/like/" + Number(postId2),
            dataType: "json"
        }).done(function (data) {
            console.log(data);
            $("#createLike").css('background-color', 'rgb(255, 255, 0)');
            $("#likeCnt").text(data["like_cnt"]);
        }).fail(function (request, status, error) {
            console.log(request.status + " : " +
                request.responseText + " : " + error);
        });
    }
};