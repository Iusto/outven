// joinForm.js
$(function() {
  // 아이디 중복 검사
  const idInput = $("#memberId");
  const idLabel = $("#idLabel");
  idInput.on("input", function() {
    const id = idInput.val();
    const engNumRegex = /^[a-zA-Z0-9]+$/;
    const koreanRegex = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;

    if (id.length < 4) {
      idLabel.text("아이디는 최소 4자 이상이어야 합니다.").css("color", "red");
      return;
    }
    if (koreanRegex.test(id)) {
      idLabel.text("아이디에 한글은 사용할 수 없습니다.").css("color", "red");
      return;
    }
    if (!engNumRegex.test(id)) {
      idLabel.text("아이디는 영어와 숫자만 사용할 수 있습니다.").css("color", "red");
      return;
    }
    fetch(`/member/checkId?id=${id}`)
      .then(res => res.json())
      .then(exists => {
        if (exists) {
          idLabel.text("이미 사용 중인 아이디입니다.").css("color", "red");
        } else {
          idLabel.text("사용 가능한 아이디입니다.").css("color", "green");
        }
      });
  });

  // 닉네임 중복 검사
  const nickInput = $("#nickName");
  const nickLabel = $("#nickLabel");
  nickInput.on("input", function() {
    const nick = nickInput.val();
    if (nick.length < 2) {
      nickLabel.text("닉네임은 최소 2자 이상이어야 합니다.").css("color", "red");
      return;
    }
    fetch(`/member/checkNickName?nickName=${nick}`)
      .then(res => res.json())
      .then(exists => {
        if (exists) {
          nickLabel.text("이미 사용 중인 닉네임입니다.").css("color", "red");
        } else {
          nickLabel.text("사용 가능한 닉네임입니다.").css("color", "green");
        }
      });
  });

  // 이메일 중복 검사 및 인증번호 요청
  const emailInput = $("#email");
  const emailLabel = $("#emailLabel");
  const sendCodeBtn = $("#sendCodeBtn");
  emailInput.on("input", function() {
    const email = emailInput.val();
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailRegex.test(email)) {
      emailLabel.text("올바른 이메일 형식이 아닙니다.").css("color", "red");
      sendCodeBtn.prop("disabled", true);
      return;
    }
    fetch(`/member/checkEmail?email=${email}`)
      .then(res => res.json())
      .then(exists => {
        if (exists) {
          emailLabel.text("이미 사용 중인 이메일입니다.").css("color", "red");
          sendCodeBtn.prop("disabled", true);
        } else {
          emailLabel.text("사용 가능한 이메일입니다.").css("color", "green");
          sendCodeBtn.prop("disabled", false);
        }
      });
  });

  sendCodeBtn.on("click", function() {
    const email = emailInput.val();
    fetch(`/email/sendCode?email=${email}`)
      .then(res => res.text())
      .then(message => {
        alert("인증번호가 이메일로 발송되었습니다.");
      });
  });

  // 생년월일 숫자 입력 제한
  $("#year, #month, #day").on("input", function() {
    this.value = this.value.replace(/[^0-9]/g, '');
  });

  // 주소 검색창 클릭 시 자동으로 검색 창 오픈
  $("#address1").on("click", function() {
    execDaumPostcode();
  });

  // 비밀번호 일치 확인
  const passwordInput = $("#password");
  const passwordConfirmInput = $("#passwordConfirm");
  const passwordConfirmLabel = $("#passwordConfirmLabel");

  passwordConfirmInput.on("keyup", function() {
    if (passwordInput.val() !== passwordConfirmInput.val()) {
      passwordConfirmLabel.text("비밀번호가 일치하지 않습니다.").css("color", "red");
    } else {
      passwordConfirmLabel.text("비밀번호가 일치합니다.").css("color", "green");
    }
  });

  // 폼 제출 전 체크
  $("form").submit(function(event) {
    const emailVerified = emailLabel.text() === "사용 가능한 이메일입니다.";

    if (!emailVerified) {
      alert("이메일 인증이 완료되지 않았습니다.");
      event.preventDefault();
      return;
    }

    if (passwordInput.val() !== passwordConfirmInput.val()) {
      alert("비밀번호가 일치하지 않습니다.");
      event.preventDefault();
      return;
    }

    const addr1 = $("#address1").val();
    const addr2 = $("#address2").val();
    $("#address").val(addr1 + " " + addr2);
  });

  // 비밀번호 토글
  $("#togglePassword").on("click", function() {
    const type = passwordInput.attr("type") === "password" ? "text" : "password";
    passwordInput.attr("type", type);
    $(this).toggleClass("bi-eye").toggleClass("bi-eye-slash");
  });

});
