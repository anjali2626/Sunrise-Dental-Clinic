document.addEventListener("DOMContentLoaded", function () {

    const loginForm =
        document.getElementById("loginForm");

    const togglePassword =
        document.getElementById("togglePassword");

    const passwordInput =
        document.getElementById("password");

    const loginMessage =
        document.getElementById("loginMessage");



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

        loginForm.addEventListener("submit", async function (event) {

            event.preventDefault();


            const username =
                document
                    .getElementById("username")
                    .value
                    .trim();


            const password =
                document
                    .getElementById("password")
                    .value;


            if (username === "" || password === "") {

                showLoginMessage(
                    "Please enter both username and password.",
                    "danger"
                );

                return;
            }



            showLoginMessage(
                "Signing in...",
                "info"
            );



            try {

                const response = await fetch(
                    "http://localhost:8080/api/login",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/x-www-form-urlencoded"
                        },

                        body:
                            new URLSearchParams({
                                username: username,
                                password: password
                            })
                    }
                );



                const data =
                    await response.json();


                if (
                    response.ok &&
                    data.success === true
                ) {

                    showLoginMessage(
                        "Login successful. Redirecting...",
                        "success"
                    );


                   
                    sessionStorage.setItem(
                        "username",
                        data.username
                    );


                    sessionStorage.setItem(
                        "fullName",
                        data.fullName
                    );


                    sessionStorage.setItem(
                        "role",
                        data.role
                    );



                    setTimeout(function () {

                        window.location.href =
                            "dashboard.html";

                    }, 800);


                } else {

                   
                    showLoginMessage(
                        data.message ||
                        "Invalid username or password.",
                        "danger"
                    );

                }


            } catch (error) {

                console.error(
                    "Login error:",
                    error
                );


                showLoginMessage(
                    "Unable to connect to the backend server. " +
                    "Please make sure the Java server is running.",
                    "danger"
                );

            }

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