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


    loadPatients();
    loadDentists();
    loadTreatments();
    loadAppointments();


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


            appointmentTableBody.innerHTML = "";


            appointments.forEach(function (appointment) {

                const row =
                    document.createElement("tr");

                const appointmentNumberCell =
                    document.createElement("td");

                appointmentNumberCell.textContent =
                    appointment.appointmentNumber;

                const patientCell =
                    document.createElement("td");

                patientCell.textContent =
                    patientMap[appointment.patientId]
                    || "Unknown Patient";

                const dentistCell =
                    document.createElement("td");

                dentistCell.textContent =
                    dentistMap[appointment.dentistId]
                    || "Unknown Dentist";

                const treatmentCell =
                    document.createElement("td");

                treatmentCell.textContent =
                    treatmentMap[appointment.treatmentId]
                    || "Unknown Treatment";

                const dateCell =
                    document.createElement("td");

                dateCell.textContent =
                    appointment.appointmentDate;

                const timeCell =
                    document.createElement("td");

                timeCell.textContent =
                    formatTime(
                        appointment.appointmentTime
                    );

                const statusCell =
                    document.createElement("td");

                const statusBadge =
                    document.createElement("span");

                statusBadge.classList.add("badge");

                const status =
                    appointment.status || "Scheduled";

                statusBadge.textContent =
                    status;

					

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


                const submitButton =
                    form.querySelector(
                        'button[type="submit"]'
                    );

                submitButton.disabled =
                    true;

                submitButton.textContent =
                    "Saving...";


                try {

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


                  

                    const result =
                        await response.json();


                   
                    if (!response.ok) {

                        throw new Error(
                            result.message ||
                            "Unable to create appointment."
                        );
                    }


                    alert(
                        "Appointment added successfully."
                    );


                    form.reset();


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

                   

                    submitButton.disabled =
                        false;

                    submitButton.textContent =
                        "Save Appointment";
                }
            }
        );
    }



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