document.addEventListener("DOMContentLoaded", function () {

    const API_BASE_URL = "http://localhost:8080/api";

    const form =
        document.getElementById("appointmentForm");

    const patientSelect =
        document.getElementById("patient");

    const dentistSelect =
        document.getElementById("dentist");

    const treatmentSelect =
        document.getElementById("treatment");

    const appointmentTableBody =
        document.querySelector("#appointmentTable tbody");

    const search =
        document.getElementById("appointmentSearch");


    /*
     * =========================================================
     * LOAD DATA WHEN PAGE OPENS
     * =========================================================
     */

    loadPatients();
    loadDentists();
    loadTreatments();
    loadAppointments();


    /*
     * =========================================================
     * LOAD PATIENTS
     * =========================================================
     */

    async function loadPatients() {

        try {

            const response =
                await fetch(
                    API_BASE_URL + "/patients"
                );

            if (!response.ok) {

                throw new Error(
                    "Failed to load patients."
                );
            }

            const patients =
                await response.json();

            patientSelect.innerHTML =
                '<option value="">Select patient</option>';

            patients.forEach(function (patient) {

                const option =
                    document.createElement("option");

                option.value =
                    patient.patientId;

                option.textContent =
                    patient.patientName;

                patientSelect.appendChild(option);
            });

        } catch (error) {

            console.error(
                "Error loading patients:",
                error
            );

            alert(
                "Unable to load patients from the backend."
            );
        }
    }


    /*
     * =========================================================
     * LOAD DENTISTS
     * =========================================================
     */

    async function loadDentists() {

        try {

            const response =
                await fetch(
                    API_BASE_URL + "/dentists"
                );

            if (!response.ok) {

                throw new Error(
                    "Failed to load dentists."
                );
            }

            const dentists =
                await response.json();

            dentistSelect.innerHTML =
                '<option value="">Select dentist</option>';

            dentists
                .filter(function (dentist) {

                    return dentist.active === true;

                })
                .forEach(function (dentist) {

                    const option =
                        document.createElement("option");

                    option.value =
                        dentist.dentistId;

                    option.textContent =
                        dentist.dentistName;

                    dentistSelect.appendChild(option);
                });

        } catch (error) {

            console.error(
                "Error loading dentists:",
                error
            );

            alert(
                "Unable to load dentists from the backend."
            );
        }
    }


    /*
     * =========================================================
     * LOAD TREATMENTS
     * =========================================================
     */

    async function loadTreatments() {

        try {

            const response =
                await fetch(
                    API_BASE_URL + "/treatments"
                );

            if (!response.ok) {

                throw new Error(
                    "Failed to load treatments."
                );
            }

            const treatments =
                await response.json();

            treatmentSelect.innerHTML =
                '<option value="">Select treatment</option>';

            treatments
                .filter(function (treatment) {

                    return treatment.active === true;

                })
                .forEach(function (treatment) {

                    const option =
                        document.createElement("option");

                    option.value =
                        treatment.treatmentId;

                    option.textContent =
                        treatment.treatmentName;

                    treatmentSelect.appendChild(option);
                });

        } catch (error) {

            console.error(
                "Error loading treatments:",
                error
            );

            alert(
                "Unable to load treatments from the backend."
            );
        }
    }


    /*
     * =========================================================
     * LOAD APPOINTMENTS
     * =========================================================
     */

    async function loadAppointments() {

        try {

            const response =
                await fetch(
                    API_BASE_URL + "/appointments"
                );

            if (!response.ok) {

                throw new Error(
                    "Failed to load appointments."
                );
            }

            const appointments =
                await response.json();

            /*
             * We also need patient, dentist and treatment
             * information so that IDs can be converted into
             * readable names.
             */

            const patientsResponse =
                await fetch(
                    API_BASE_URL + "/patients"
                );

            const dentistsResponse =
                await fetch(
                    API_BASE_URL + "/dentists"
                );

            const treatmentsResponse =
                await fetch(
                    API_BASE_URL + "/treatments"
                );

            if (
                !patientsResponse.ok ||
                !dentistsResponse.ok ||
                !treatmentsResponse.ok
            ) {

                throw new Error(
                    "Failed to load appointment reference data."
                );
            }

            const patients =
                await patientsResponse.json();

            const dentists =
                await dentistsResponse.json();

            const treatments =
                await treatmentsResponse.json();


            /*
             * Create lookup maps.
             */

            const patientMap = {};

            patients.forEach(function (patient) {

                patientMap[patient.patientId] =
                    patient.patientName;
            });


            const dentistMap = {};

            dentists.forEach(function (dentist) {

                dentistMap[dentist.dentistId] =
                    dentist.dentistName;
            });


            const treatmentMap = {};

            treatments.forEach(function (treatment) {

                treatmentMap[treatment.treatmentId] =
                    treatment.treatmentName;
            });


            /*
             * Clear existing table rows.
             */

            appointmentTableBody.innerHTML = "";


            /*
             * Display appointments.
             */

            appointments.forEach(function (appointment) {

                const row =
                    document.createElement("tr");


                /*
                 * Appointment Number
                 */

                const appointmentNumberCell =
                    document.createElement("td");

                appointmentNumberCell.textContent =
                    appointment.appointmentNumber;


                /*
                 * Patient
                 */

                const patientCell =
                    document.createElement("td");

                patientCell.textContent =
                    patientMap[appointment.patientId]
                    || "Unknown Patient";


                /*
                 * Dentist
                 */

                const dentistCell =
                    document.createElement("td");

                dentistCell.textContent =
                    dentistMap[appointment.dentistId]
                    || "Unknown Dentist";


                /*
                 * Treatment
                 */

                const treatmentCell =
                    document.createElement("td");

                treatmentCell.textContent =
                    treatmentMap[appointment.treatmentId]
                    || "Unknown Treatment";


                /*
                 * Date
                 */

                const dateCell =
                    document.createElement("td");

                dateCell.textContent =
                    appointment.appointmentDate;


                /*
                 * Time
                 */

                const timeCell =
                    document.createElement("td");

                timeCell.textContent =
                    formatTime(
                        appointment.appointmentTime
                    );


                /*
                 * Status
                 */

                const statusCell =
                    document.createElement("td");

                const statusBadge =
                    document.createElement("span");

                statusBadge.classList.add("badge");

                const status =
                    appointment.status || "Scheduled";

                statusBadge.textContent =
                    status;


                /*
                 * Apply badge style according to status.
                 */

                if (
                    status.toLowerCase() ===
                    "scheduled"
                ) {

                    statusBadge.classList.add(
                        "badge-success"
                    );

                } else if (
                    status.toLowerCase() ===
                    "confirmed"
                ) {

                    statusBadge.classList.add(
                        "badge-info"
                    );

                } else if (
                    status.toLowerCase() ===
                    "waiting"
                ) {

                    statusBadge.classList.add(
                        "badge-warning"
                    );

                } else if (
                    status.toLowerCase() ===
                    "cancelled"
                ) {

                    statusBadge.classList.add(
                        "badge-danger"
                    );

                } else {

                    statusBadge.classList.add(
                        "badge-info"
                    );
                }


                statusCell.appendChild(
                    statusBadge
                );


                /*
                 * Add cells to row.
                 */

                row.appendChild(
                    appointmentNumberCell
                );

                row.appendChild(
                    patientCell
                );

                row.appendChild(
                    dentistCell
                );

                row.appendChild(
                    treatmentCell
                );

                row.appendChild(
                    dateCell
                );

                row.appendChild(
                    timeCell
                );

                row.appendChild(
                    statusCell
                );


                /*
                 * Add row to table.
                 */

                appointmentTableBody.appendChild(
                    row
                );
            });

        } catch (error) {

            console.error(
                "Error loading appointments:",
                error
            );

            appointmentTableBody.innerHTML =
                '<tr>' +
                '<td colspan="7" style="text-align:center;">' +
                'Unable to load appointments from the backend.' +
                '</td>' +
                '</tr>';
        }
    }


    /*
     * =========================================================
     * FORMAT TIME
     * =========================================================
     */

    function formatTime(time) {

        if (!time) {
            return "";
        }

        const parts =
            time.split(":");

        if (parts.length < 2) {
            return time;
        }

        let hours =
            parseInt(parts[0], 10);

        const minutes =
            parts[1];

        const amPm =
            hours >= 12
                ? "PM"
                : "AM";

        hours =
            hours % 12;

        if (hours === 0) {
            hours = 12;
        }

        return (
            String(hours).padStart(2, "0") +
            ":" +
            minutes +
            " " +
            amPm
        );
    }


    /*
     * =========================================================
     * CREATE NEW APPOINTMENT
     * =========================================================
     */

    if (form) {

        form.addEventListener(
            "submit",
            async function (event) {

                event.preventDefault();


                const appointmentNumber =
                    document
                        .getElementById(
                            "appointmentNumber"
                        )
                        .value
                        .trim();


                const patientId =
                    patientSelect.value;


                const dentistId =
                    dentistSelect.value;


                const treatmentId =
                    treatmentSelect.value;


                const date =
                    document
                        .getElementById(
                            "appointmentDate"
                        )
                        .value;


                const time =
                    document
                        .getElementById(
                            "appointmentTime"
                        )
                        .value;


                /*
                 * Required field validation.
                 */

                if (
                    !appointmentNumber ||
                    !patientId ||
                    !dentistId ||
                    !treatmentId ||
                    !date ||
                    !time
                ) {

                    alert(
                        "Please complete all appointment fields."
                    );

                    return;
                }


                /*
                 * Disable submit button while sending.
                 */

                const submitButton =
                    form.querySelector(
                        'button[type="submit"]'
                    );

                submitButton.disabled =
                    true;

                submitButton.textContent =
                    "Saving...";


                try {

                    /*
                     * The Java backend expects
                     * application/x-www-form-urlencoded
                     * data.
                     */

                    const formData =
                        new URLSearchParams();

                    formData.append(
                        "appointmentNumber",
                        appointmentNumber
                    );

                    formData.append(
                        "patientId",
                        patientId
                    );

                    formData.append(
                        "dentistId",
                        dentistId
                    );

                    formData.append(
                        "treatmentId",
                        treatmentId
                    );

                    formData.append(
                        "appointmentDate",
                        date
                    );

                    formData.append(
                        "appointmentTime",
                        time
                    );


                    /*
                     * Send POST request.
                     */

                    const response =
                        await fetch(
                            API_BASE_URL +
                            "/appointments",
                            {
                                method: "POST",

                                headers: {
                                    "Content-Type":
                                        "application/x-www-form-urlencoded"
                                },

                                body:
                                    formData.toString()
                            }
                        );


                    /*
                     * Read backend response.
                     */

                    const result =
                        await response.json();


                    /*
                     * Handle failed request.
                     */

                    if (!response.ok) {

                        throw new Error(
                            result.message ||
                            "Unable to create appointment."
                        );
                    }


                    /*
                     * Appointment successfully created.
                     */

                    alert(
                        "Appointment added successfully."
                    );


                    /*
                     * Clear form.
                     */

                    form.reset();


                    /*
                     * Reload appointment table
                     * from MySQL.
                     */

                    await loadAppointments();


                } catch (error) {

                    console.error(
                        "Error creating appointment:",
                        error
                    );

                    alert(
                        error.message ||
                        "Unable to create appointment."
                    );


                } finally {

                    /*
                     * Enable button again.
                     */

                    submitButton.disabled =
                        false;

                    submitButton.textContent =
                        "Save Appointment";
                }
            }
        );
    }


    /*
     * =========================================================
     * APPOINTMENT SEARCH
     * =========================================================
     */

    if (search) {

        search.addEventListener(
            "keyup",
            function () {

                const value =
                    search.value
                        .toLowerCase()
                        .trim();


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
            }
        );
    }

});