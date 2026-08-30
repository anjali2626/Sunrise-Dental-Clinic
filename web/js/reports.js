document.addEventListener("DOMContentLoaded", function () {

    const API_BASE_URL =
        "http://localhost:8080/api/reports";


    const reportDate =
        document.getElementById(
            "reportDate"
        );

    const reportPeriod =
        document.getElementById(
            "reportPeriod"
        );

    const generateAppointmentReport =
        document.getElementById(
            "generateAppointmentReport"
        );

    const generateRevenueReport =
        document.getElementById(
            "generateRevenueReport"
        );

    const dailyAppointmentTable =
        document.querySelector(
            "#dailyAppointmentTable tbody"
        );

    const revenueReportTable =
        document.querySelector(
            "#revenueReportTable tbody"
        );

    const dailyReportDate =
        document.getElementById(
            "dailyReportDate"
        );

    const totalTreatments =
        document.getElementById(
            "totalTreatments"
        );

    const totalRevenue =
        document.getElementById(
            "totalRevenue"
        );


    function setTodayDate() {

        const today =
            new Date();

        const year =
            today.getFullYear();

        const month =
            String(
                today.getMonth() + 1
            ).padStart(
                2,
                "0"
            );

        const day =
            String(
                today.getDate()
            ).padStart(
                2,
                "0"
            );

        reportDate.value =
            `${year}-${month}-${day}`;
    }


    function formatDate(dateValue) {

        if (!dateValue) {
            return "";
        }

        const date =
            new Date(
                dateValue + "T00:00:00"
            );

        return date.toLocaleDateString(
            "en-GB",
            {
                day: "2-digit",
                month: "long",
                year: "numeric"
            }
        );
    }


    function formatTime(timeValue) {

        if (!timeValue) {
            return "";
        }

        const timeParts =
            timeValue.split(":");

        let hours =
            parseInt(
                timeParts[0]
            );

        const minutes =
            timeParts[1];

        const period =
            hours >= 12
                ? "PM"
                : "AM";

        hours =
            hours % 12;

        if (hours === 0) {
            hours = 12;
        }

        return `${String(hours).padStart(
            2,
            "0"
        )}:${minutes} ${period}`;
    }


    function getStatusBadge(status) {

        const normalizedStatus =
            status
                ? status.toUpperCase()
                : "";

        let badgeClass =
            "badge-info";

        if (
            normalizedStatus ===
            "SCHEDULED"
        ) {

            badgeClass =
                "badge-success";

        } else if (
            normalizedStatus ===
            "CONFIRMED"
        ) {

            badgeClass =
                "badge-info";

        } else if (
            normalizedStatus ===
            "CANCELLED"
        ) {

            badgeClass =
                "badge-danger";
        }

        return `
            <span class="badge ${badgeClass}">
                ${status}
            </span>
        `;
    }


    async function loadDailyAppointmentReport() {

        const selectedDate =
            reportDate.value;

        if (!selectedDate) {

            alert(
                "Please select a date."
            );

            return;
        }

        dailyAppointmentTable.innerHTML = `
            <tr>
                <td
                    colspan="6"
                    style="text-align:center;">
                    Loading report...
                </td>
            </tr>
        `;

        try {

            const response =
                await fetch(
                    `${API_BASE_URL}/daily-appointments?date=${selectedDate}`
                );

            const data =
                await response.json();

            if (!response.ok) {

                throw new Error(
                    data.message ||
                    "Failed to load appointment report."
                );
            }

            dailyReportDate.textContent =
                formatDate(
                    selectedDate
                );

            dailyAppointmentTable.innerHTML =
                "";

            if (data.length === 0) {

                dailyAppointmentTable.innerHTML = `
                    <tr>
                        <td
                            colspan="6"
                            style="text-align:center;">
                            No appointments found for this date.
                        </td>
                    </tr>
                `;

                return;
            }

            data.forEach(
                function (appointment) {

                    const row =
                        document.createElement(
                            "tr"
                        );

                    row.innerHTML = `
                        <td>
                            ${appointment.appointmentNumber}
                        </td>

                        <td>
                            ${appointment.patientName}
                        </td>

                        <td>
                            ${appointment.dentistName}
                        </td>

                        <td>
                            ${appointment.treatmentName}
                        </td>

                        <td>
                            ${formatTime(
                                appointment.appointmentTime
                            )}
                        </td>

                        <td>
                            ${getStatusBadge(
                                appointment.status
                            )}
                        </td>
                    `;

                    dailyAppointmentTable
                        .appendChild(
                            row
                        );
                }
            );

        } catch (error) {

            console.error(error);

            dailyAppointmentTable.innerHTML = `
                <tr>
                    <td
                        colspan="6"
                        style="text-align:center;">
                        Unable to load appointment report.
                    </td>
                </tr>
            `;

            alert(
                error.message
            );
        }
    }


    async function loadTreatmentRevenueReport() {

        const selectedPeriod =
            reportPeriod.value;

        revenueReportTable.innerHTML = `
            <tr>
                <td
                    colspan="3"
                    style="text-align:center;">
                    Loading report...
                </td>
            </tr>
        `;

        try {

            const response =
                await fetch(
                    `${API_BASE_URL}/treatment-revenue?period=${selectedPeriod}`
                );

            const data =
                await response.json();

            if (!response.ok) {

                throw new Error(
                    data.message ||
                    "Failed to load revenue report."
                );
            }

            revenueReportTable.innerHTML =
                "";

            let treatmentsCount =
                0;

            let revenueTotal =
                0;

            if (data.length === 0) {

                revenueReportTable.innerHTML = `
                    <tr>
                        <td
                            colspan="3"
                            style="text-align:center;">
                            No revenue data found for this period.
                        </td>
                    </tr>
                `;

                totalTreatments.textContent =
                    "0";

                totalRevenue.textContent =
                    "Rs. 0.00";

                return;
            }

            data.forEach(
                function (report) {

                    const treatmentCount =
                        Number(
                            report.numberOfTreatments
                        ) || 0;

                    const revenue =
                        Number(
                            report.revenue
                        ) || 0;

                    treatmentsCount +=
                        treatmentCount;

                    revenueTotal +=
                        revenue;

                    const row =
                        document.createElement(
                            "tr"
                        );

                    row.innerHTML = `
                        <td>
                            ${report.treatmentName}
                        </td>

                        <td>
                            ${treatmentCount}
                        </td>

                        <td>
                            Rs. ${revenue.toFixed(2)}
                        </td>
                    `;

                    revenueReportTable
                        .appendChild(
                            row
                        );
                }
            );

            totalTreatments.textContent =
                treatmentsCount;

            totalRevenue.textContent =
                `Rs. ${revenueTotal.toFixed(2)}`;

        } catch (error) {

            console.error(error);

            revenueReportTable.innerHTML = `
                <tr>
                    <td
                        colspan="3"
                        style="text-align:center;">
                        Unable to load revenue report.
                    </td>
                </tr>
            `;

            totalTreatments.textContent =
                "0";

            totalRevenue.textContent =
                "Rs. 0.00";

            alert(
                error.message
            );
        }
    }


    if (reportDate) {

        setTodayDate();
    }


    if (generateAppointmentReport) {

        generateAppointmentReport
            .addEventListener(
                "click",
                function () {

                    loadDailyAppointmentReport();
                }
            );
    }


    if (generateRevenueReport) {

        generateRevenueReport
            .addEventListener(
                "click",
                function () {

                    loadTreatmentRevenueReport();
                }
            );
    }

});