// 로그인 버튼 클릭 시 폼 표시/숨김 토글
document.getElementById("showSignUpForm").addEventListener("click", function() {
    var signupForm = document.getElementById("signupForm");
    if (signupForm.style.display === "none" || signupForm.style.display === "") {
        signupForm.style.display = "block";
    } else {
        signupForm.style.display = "none";
    }
});
