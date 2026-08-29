document.addEventListener("DOMContentLoaded", function () {

    const API_URL = "http://localhost:8080/api/patients";

    const form = document.getElementById("addPatientForm");
    const addPatientSection =
        document.getElementById("addPatientSection");

    const showAddPatientBtn =
        document.getElementById("showAddPatientBtn");

    const cancelAddPatientBtn =
        document.getElementById("cancelAddPatientBtn");

    const patientTableBody =
        document.querySelector("#patientTable tbody");

    const patientSearch =
        document.getElementById("patientSearch");


    /* =====================================================
       LOAD PATIENTS FROM BACKEND
       ===================================================== */

    loadPatients();


    async function loadPatients() {

        try {

            const response =
                await fetch(API_URL);

            if (!response.ok) {

                throw new Error(
                    "Failed to load patients."
                );
            }

            const patients =
                await response.json();

            displayPatients(patients);

        } catch (error) {

            console.error(
                "Error loading patients:",
                error
            );

            patientTableBody.innerHTML = `
                <tr>
                    <td colspan="5"
                        style="text-align:center;color:#dc2626;">
                        Unable to load patient records.
                    </td>
                </tr>
            `;
        }
    }


    /* =====================================================
       DISPLAY PATIENTS
       ===================================================== */

    function displayPatients(patients) {

        patientTableBody.innerHTML = "";

        if (!patients || patients.length === 0) {

            patientTableBody.innerHTML = `
                <tr>
                    <td colspan="5"
                        style="text-align:center;color:#64748b;">
                        No patient records found.
                    </td>
                </tr>
            `;

            return;
        }


        patients.forEach(function (patient) {

            const row =
                document.createElement("tr");

            row.innerHTML = `

                <td>${escapeHtml(patient.patientId)}</td>

                <td>${escapeHtml(patient.patientName)}</td>

                <td>${escapeHtml(patient.address)}</td>

                <td>${escapeHtml(patient.contactNumber)}</td>

                <td>
                    <button
                        type="button"
                        class="btn btn-secondary btn-sm"
                        onclick="viewPatient(${patient.patientId})">
                        View
                    </button>
                </td>

            `;

            patientTableBody.appendChild(row);

        });
    }


    /* =====================================================
       SHOW ADD PATIENT SECTION
       ===================================================== */

    if (showAddPatientBtn) {

        showAddPatientBtn.addEventListener(
            "click",
            function () {

                addPatientSection.style.display =
                    "block";

                document
                    .getElementById("patientName")
                    .focus();

            }
        );

    }


    /* =====================================================
       CANCEL ADD PATIENT
       ===================================================== */

    if (cancelAddPatientBtn) {

        cancelAddPatientBtn.addEventListener(
            "click",
            function () {

                form.reset();

                addPatientSection.style.display =
                    "none";

            }
        );

    }


    /* =====================================================
       ADD PATIENT
       ===================================================== */

    if (form) {

        form.addEventListener(
            "submit",
            async function (event) {

                event.preventDefault();


                const patientName =
                    document
                        .getElementById("patientName")
                        .value
                        .trim();


                const address =
                    document
                        .getElementById("address")
                        .value
                        .trim();


                const contactNumber =
                    document
                        .getElementById("contactNumber")
                        .value
                        .trim();


                /* -----------------------------------------
                   FRONTEND VALIDATION
                   ----------------------------------------- */

                if (
                    patientName === "" ||
                    address === "" ||
                    contactNumber === ""
                ) {

                    alert(
                        "Please complete all patient fields."
                    );

                    return;
                }


                if (!/^\d{10}$/.test(contactNumber)) {

                    alert(
                        "Contact number must contain exactly 10 digits."
                    );

                    return;
                }


                try {

                    const formData =
                        new URLSearchParams();

                    formData.append(
                        "patientName",
                        patientName
                    );

                    formData.append(
                        "address",
                        address
                    );

                    formData.append(
                        "contactNumber",
                        contactNumber
                    );


                    const response =
                        await fetch(
                            API_URL,
                            {
                                method: "POST",

                                headers: {
                                    "Content-Type":
                                        "application/x-www-form-urlencoded"
                                },

                                body: formData.toString()
                            }
                        );


                    const result =
                        await response.json();


                    if (!response.ok) {

                        throw new Error(
                            result.message ||
                            "Unable to add patient."
                        );
                    }


                    /* -------------------------------------
                       SUCCESS
                       ------------------------------------- */

                    alert(
                        "Patient added successfully."
                    );


                    form.reset();


                    addPatientSection.style.display =
                        "none";


                    /* Refresh patient records */

                    loadPatients();


                } catch (error) {

                    console.error(
                        "Error adding patient:",
                        error
                    );

                    alert(
                        error.message ||
                        "Unable to add patient."
                    );
                }

            }
        );

    }


    /* =====================================================
       SEARCH PATIENTS
       ===================================================== */

    if (patientSearch) {

        patientSearch.addEventListener(
            "keyup",
            function () {

                const value =
                    this.value
                        .toLowerCase()
                        .trim();


                const rows =
                    document.querySelectorAll(
                        "#patientTable tbody tr"
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


    /* =====================================================
       VIEW PATIENT
       ===================================================== */

    window.viewPatient = async function (patientId) {

        try {

            const response =
                await fetch(
                    API_URL + "/" + patientId
                );


            if (!response.ok) {

                throw new Error(
                    "Patient record could not be found."
                );
            }


            const patient =
                await response.json();


            alert(
                "Patient Details\n\n" +
                "Patient ID: " +
                patient.patientId +
                "\n" +
                "Patient Name: " +
                patient.patientName +
                "\n" +
                "Address: " +
                patient.address +
                "\n" +
                "Contact Number: " +
                patient.contactNumber
            );


        } catch (error) {

            console.error(
                "Error loading patient:",
                error
            );

            alert(
                error.message ||
                "Unable to load patient details."
            );

        }

    };


    /* =====================================================
       HTML ESCAPING
       ===================================================== */

    function escapeHtml(value) {

        if (value === null ||
            value === undefined) {

            return "";
        }

        return String(value)
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;")
            .replace(/'/g, "&#039;");
    }

});