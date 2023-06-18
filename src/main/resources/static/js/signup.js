$(document).ready(function() {
    $('#signup').click(function() {
        if ($('#userid').val() == "") {
            alert("아이디를 입력해주세요.");
            return false;
        }

        if ($('#username').val() == "") {
            alert("유저명을 입력해주세요.");
            return false;
        }

        if ($('#password').val() == "") {
            alert("비밀번호를 입력해주세요.");
            return false;
        }

        if ($('#email_auth_key').val() == "") {
            alert("인증번호를 입력해주세요.");
            return false;
        }
    });

    $("#email_auth_btn").click(function() {
        var email = $('#email').val();
        var userid = $('#userid').val();

        if (userid == '') {
            alert("아이디를 입력해주세요.");
            return false;
        }

        if (email == '') {
            alert("이메일을 입력해주세요.");
            return false;
        }

        $.ajax({
            type: "POST",
            url: "/sendCode",
            contentType: "application/json",
            data: JSON.stringify({
                "email": email,
                "userid": userid
            }),
            success: function() {
                alert("인증번호가 발송되었습니다.");
            },
            error: function() {
                alert("메일 발송에 실패했습니다.");
            }
        });
    });
});
