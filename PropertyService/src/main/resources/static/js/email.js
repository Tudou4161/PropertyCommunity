$(document).ready(function () {
    $("#sendMail").bind('click', function () {
        sendEmail();
    });
});

var sendEmail = function (){

    //$("#sendMail").unbind('click');

    var emailAddr = document.getElementById('mail').value;

    if (emailAddr == "" && emailAddr == " ") {
        alert("이메일 인증을 진행해주세요.");
    } else {
        var emailAddr = document.getElementById('mail').value;
        console.log(emailAddr);

        $.ajax({
            type: "GET",
            url: "/email/checkEmail/" + emailAddr,
            dataType: "json"
            }).done(function(data) {
                alert("인증번호가 전송되었습니다.");
                console.log(data.result);
                console.log(data.email);
                console.log(data.content);
                //var code = $('#authCode').val(data.content);
                console.log(code);

            }).fail(function(request,status,error){
                alert("실패!");
                console.log(request.status + " : " +
                    request.responseText + " : " + error)
            });
        };
    };


document.getElementById('check-btn').addEventListener('click', function (e) {
    var isCertification = false;
    var code = $('#authCode').val();
    var isCertification = $('#isCertificate').val();

    if (code == $('#checkCode').val()) {
        $("#compare-text").val("인증 성공!").css("color", 'rgb(255, 255, 255)');
        isCertification = $('#isCertificate').val("true");
        console.log(isCertification);
    } else {
        $("#compare-text").val("인증 실패!").css("color", 'rgb(255, 255, 255)');
        isCertification = $('#isCertificate').val("false");
        console.log(isCertification);
    }
});

$(".submit-btn").click(function submitCheck(){
		if($('#isCertificate').val()=="false"){
			alert("메일 인증이 완료되지 않았습니다.");
			return false;
		} else {
		    return true;
		}
	});