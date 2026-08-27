document.addEventListener("DOMContentLoaded", function () {

    const buttons =
        document.querySelectorAll(".btn-primary");

    buttons.forEach(function (button) {

        if (
            button.textContent.includes("Generate")
        ) {

            button.addEventListener("click", function () {

                alert(
                    "Report interface is working.\n\n" +
                    "Report data will be connected to the " +
                    "Java backend API later."
                );

            });

        }

    });

});