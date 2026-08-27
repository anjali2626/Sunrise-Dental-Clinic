document.addEventListener("DOMContentLoaded", function () {

    const loginForm = document.getElementById("loginForm");
    const togglePassword = document.getElementById("togglePassword");
    const passwordInput = document.getElementById("password");
    const loginMessage = document.getElementById("loginMessage");

    if (togglePassword) {

        togglePassword.addEventListener("click", function () {

            if (passwordInput.type === "password") {
                passwordInput.type = "text";
                togglePassword.textContent = "🙈";
            } else {
                passwordInput.type = "password";
                togglePassword.textContent = "👁";
            }

        });
    }

    if (loginForm) {

        loginForm.addEventListener("submit", function (event) {

            event.preventDefault();

            const username =
                document.getElementById("username").value.trim();

            const password =
                document.getElementById("password").value;

            if (username === "" || password === "") {
                showLoginMessage(
                    "Please enter both username and password.",
                    "danger"
                );
                return;
            }

            /*
             * Frontend design/testing stage.
             *
             * API authentication will be connected later.
             */

            showLoginMessage(
                "Frontend is ready. Backend API connection will be added in the next stage.",
                "info"
            );

        });
    }

    function showLoginMessage(message, type) {

        loginMessage.innerHTML =
            '<div class="alert alert-' +
            type +
            '">' +
            message +
            '</div>';
    }

});