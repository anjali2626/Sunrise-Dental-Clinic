document.addEventListener("DOMContentLoaded", function () {

    loadDashboard();

});


async function loadDashboard() {

    try {

        const response =
            await fetch(
                "http://localhost:8080/api/dashboard"
            );

        if (!response.ok) {

            throw new Error(
                "Failed to load dashboard data."
            );
        }

        const data =
            await response.json();

        if (!data.success) {

            throw new Error(
                "Dashboard API returned an error."
            );
        }

        /* ================================
           STATISTICS
           ================================= */

        const statNumbers =
            document.querySelectorAll(
                ".stats-grid .stat-number"
            );

        if (statNumbers.length >= 4) {

            statNumbers[0].textContent =
                data.totalPatients;

            statNumbers[1].textContent =
                data.todaysAppointments;

            statNumbers[2].textContent =
                data.activeDentists;

            statNumbers[3].textContent =
                "Rs. " +
                Number(data.todaysRevenue)
                    .toLocaleString(
                        "en-LK",
                        {
                            minimumFractionDigits: 2,
                            maximumFractionDigits: 2
                        }
                    );
        }


        /* ================================
           TODAY'S APPOINTMENTS
           ================================= */

        const tableBody =
            document.querySelector(
                ".data-table tbody"
            );

        if (!tableBody) {
            return;
        }

        tableBody.innerHTML = "";


        if (
            !data.appointments ||
            data.appointments.length === 0
        ) {

            const row =
                document.createElement("tr");

            row.innerHTML =
                '<td colspan="6" style="text-align:center;">' +
                "No appointments scheduled for today." +
                "</td>";

            tableBody.appendChild(row);

            return;
        }


        data.appointments.forEach(
            function (appointment) {

                const row =
                    document.createElement("tr");

                row.innerHTML =

                    "<td>" +
                    escapeHtml(
                        appointment.appointmentNumber
                    ) +
                    "</td>" +

                    "<td>" +
                    escapeHtml(
                        appointment.patientName
                    ) +
                    "</td>" +

                    "<td>" +
                    escapeHtml(
                        appointment.dentistName
                    ) +
                    "</td>" +

                    "<td>" +
                    escapeHtml(
                        appointment.appointmentTime
                    ) +
                    "</td>" +

                    "<td>" +
                    escapeHtml(
                        appointment.treatmentName
                    ) +
                    "</td>" +

                    "<td>" +
                    createStatusBadge(
                        appointment.status
                    ) +
                    "</td>";

                tableBody.appendChild(row);
            }
        );

    } catch (error) {

        console.error(
            "Dashboard error:",
            error
        );

        showDashboardError();
    }
}


/* =========================================
   STATUS BADGE
   ========================================= */

function createStatusBadge(status) {

    const safeStatus =
        escapeHtml(status || "");

    let badgeClass =
        "badge-info";

    const normalized =
        String(status || "")
            .toUpperCase();


    if (normalized === "SCHEDULED") {

        badgeClass =
            "badge-success";

    } else if (normalized === "CONFIRMED") {

        badgeClass =
            "badge-info";

    } else if (
        normalized === "WAITING"
    ) {

        badgeClass =
            "badge-warning";

    } else if (
        normalized === "CANCELLED"
    ) {

        badgeClass =
            "badge-danger";
    }


    return (
        '<span class="badge ' +
        badgeClass +
        '">' +
        safeStatus +
        "</span>"
    );
}


/* =========================================
   HTML ESCAPE
   ========================================= */

function escapeHtml(value) {

    const div =
        document.createElement("div");

    div.textContent =
        value == null
            ? ""
            : String(value);

    return div.innerHTML;
}


/* =========================================
   ERROR MESSAGE
   ========================================= */

function showDashboardError() {

    const statNumbers =
        document.querySelectorAll(
            ".stats-grid .stat-number"
        );

    statNumbers.forEach(
        function (element) {

            element.textContent = "--";

        }
    );

    const tableBody =
        document.querySelector(
            ".data-table tbody"
        );

    if (tableBody) {

        tableBody.innerHTML =
            '<tr>' +
            '<td colspan="6" style="text-align:center;">' +
            "Unable to load today's appointments." +
            "</td>" +
            "</tr>";
    }
}