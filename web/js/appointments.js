document.addEventListener("DOMContentLoaded", function () {

    const form =
        document.getElementById("appointmentForm");

    if (form) {

        form.addEventListener("submit", function (event) {

            event.preventDefault();

            const appointmentNumber =
                document.getElementById("appointmentNumber").value.trim();

            const patient =
                document.getElementById("patient").value;

            const dentist =
                document.getElementById("dentist").value;

            const treatment =
                document.getElementById("treatment").value;

            const date =
                document.getElementById("appointmentDate").value;

            const time =
                document.getElementById("appointmentTime").value;

            if (
                !appointmentNumber ||
                !patient ||
                !dentist ||
                !treatment ||
                !date ||
                !time
            ) {

                alert("Please complete all appointment fields.");

                return;
            }

            alert(
                "Appointment form is working.\n\n" +
                "Backend API connection will be added later."
            );

        });
    }

    const search =
        document.getElementById("appointmentSearch");

    if (search) {

        search.addEventListener("keyup", function () {

            const value =
                search.value.toLowerCase();

            const rows =
                document.querySelectorAll(
                    "#appointmentTable tbody tr"
                );

            rows.forEach(function (row) {

                row.style.display =
                    row.textContent
                    .toLowerCase()
                    .includes(value)
                    ? ""
                    : "none";

            });

        });
    }

});