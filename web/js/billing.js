document.addEventListener(
    "DOMContentLoaded",
    function () {

        const API_BASE_URL =
            "http://localhost:8080/api";

        const appointmentSelect =
            document.getElementById(
                "billAppointment");

        const consultationFee =
            document.getElementById(
                "consultationFee");

        const treatmentCost =
            document.getElementById(
                "treatmentCost");

        const totalAmount =
            document.getElementById(
                "totalAmount");

        const billingForm =
            document.getElementById(
                "billingForm");

        const billsTableBody =
            document.querySelector(
                ".data-table tbody");

        let appointments = [];
        let patients = [];
        let treatments = [];

        /*
         * Load all data when page opens
         */
        loadBillingData();

        async function loadBillingData() {

            try {

                const results =
                    await Promise.all([
                        fetch(
                            API_BASE_URL +
                            "/appointments"
                        ),
                        fetch(
                            API_BASE_URL +
                            "/patients"
                        ),
                        fetch(
                            API_BASE_URL +
                            "/treatments"
                        ),
                        fetch(
                            API_BASE_URL +
                            "/bills"
                        )
                    ]);

                const appointmentsResponse =
                    results[0];

                const patientsResponse =
                    results[1];

                const treatmentsResponse =
                    results[2];

                const billsResponse =
                    results[3];

                if (
                    !appointmentsResponse.ok ||
                    !patientsResponse.ok ||
                    !treatmentsResponse.ok ||
                    !billsResponse.ok
                ) {

                    throw new Error(
                        "Failed to load billing data."
                    );
                }

                appointments =
                    await appointmentsResponse.json();

                patients =
                    await patientsResponse.json();

                treatments =
                    await treatmentsResponse.json();

                const bills =
                    await billsResponse.json();

                populateAppointmentDropdown();

                displayBills(bills);

            } catch (error) {

                console.error(error);

                if (billsTableBody) {

                    billsTableBody.innerHTML = `
                        <tr>
                            <td colspan="7"
                                style="text-align:center;">
                                Unable to load billing data.
                            </td>
                        </tr>
                    `;
                }
            }
        }

        /*
         * Populate appointment dropdown
         */
        function populateAppointmentDropdown() {

            if (!appointmentSelect) {
                return;
            }

            appointmentSelect.innerHTML =
                `<option value="">
                    Select appointment
                </option>`;

            appointments.forEach(
                function (appointment) {

                    const patient =
                        patients.find(
                            function (patient) {

                                return patient.patientId ===
                                    appointment.patientId;
                            }
                        );

                    const patientName =
                        patient
                            ? patient.patientName
                            : "Unknown Patient";

                    const option =
                        document.createElement(
                            "option"
                        );

                    option.value =
                        appointment.appointmentId;

                    option.dataset.treatmentId =
                        appointment.treatmentId;

                    option.textContent =
                        appointment.appointmentNumber
                        + " - "
                        + patientName;

                    appointmentSelect.appendChild(
                        option
                    );
                }
            );
        }

        /*
         * Update treatment cost when
         * appointment is selected
         */
        if (appointmentSelect) {

            appointmentSelect.addEventListener(
                "change",
                function () {

                    const selectedOption =
                        appointmentSelect.options[
                            appointmentSelect
                                .selectedIndex
                        ];

                    const treatmentId =
                        selectedOption.dataset
                            .treatmentId;

                    const treatment =
                        treatments.find(
                            function (treatment) {

                                return treatment.treatmentId ===
                                    Number(
                                        treatmentId
                                    );
                            }
                        );

                    if (treatment) {

                        treatmentCost.value =
                            Number(
                                treatment.treatmentCost
                            ).toFixed(2);

                    } else {

                        treatmentCost.value =
                            "";
                    }

                    calculateTotal();
                }
            );
        }

        /*
         * Calculate total amount
         */
        function calculateTotal() {

            const consultationValue =
                parseFloat(
                    consultationFee.value
                ) || 0;

            const treatmentValue =
                parseFloat(
                    treatmentCost.value
                ) || 0;

            totalAmount.value =
                (
                    consultationValue +
                    treatmentValue
                ).toFixed(2);
        }

        /*
         * Recalculate when consultation
         * fee changes
         */
        if (consultationFee) {

            consultationFee.addEventListener(
                "input",
                calculateTotal
            );
        }

        /*
         * Submit bill
         */
        if (billingForm) {

            billingForm.addEventListener(
                "submit",
                async function (event) {

                    event.preventDefault();

                    const appointmentId =
                        appointmentSelect.value;

                    const consultationValue =
                        consultationFee.value.trim();

                    if (!appointmentId) {

                        alert(
                            "Please select an appointment."
                        );

                        return;
                    }

                    if (
                        consultationValue === "" ||
                        Number(
                            consultationValue
                        ) < 0
                    ) {

                        alert(
                            "Please enter a valid consultation fee."
                        );

                        return;
                    }

                    try {

                        const formData =
                            new URLSearchParams();

                        formData.append(
                            "appointmentId",
                            appointmentId
                        );

                        formData.append(
                            "consultationFee",
                            consultationValue
                        );

                        const response =
                            await fetch(
                                API_BASE_URL +
                                "/bills",
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

                        const responseData =
                            await response.json();

                        if (!response.ok) {

                            throw new Error(
                                responseData.message ||
                                "Failed to create bill."
                            );
                        }

                        alert(
                            "Bill created successfully!"
                        );

                        billingForm.reset();

                        treatmentCost.value =
                            "";

                        totalAmount.value =
                            "";

                        loadBillingData();

                    } catch (error) {

                        console.error(error);

                        alert(
                            error.message ||
                            "Unable to create bill."
                        );
                    }
                }
            );
        }

        /*
         * Display all bills in table
         */
        function displayBills(bills) {

            if (!billsTableBody) {
                return;
            }

            billsTableBody.innerHTML = "";

            if (
                !bills ||
                bills.length === 0
            ) {

                billsTableBody.innerHTML = `
                    <tr>
                        <td colspan="7"
                            style="text-align:center;">
                            No bills found.
                        </td>
                    </tr>
                `;

                return;
            }

            bills.forEach(
                function (bill) {

                    const appointment =
                        appointments.find(
                            function (appointment) {

                                return appointment.appointmentId ===
                                    bill.appointmentId;
                            }
                        );

                    let appointmentNumber =
                        "Unknown";

                    let patientName =
                        "Unknown";

                    if (appointment) {

                        appointmentNumber =
                            appointment.appointmentNumber;

                        const patient =
                            patients.find(
                                function (patient) {

                                    return patient.patientId ===
                                        appointment.patientId;
                                }
                            );

                        if (patient) {

                            patientName =
                                patient.patientName;
                        }
                    }

                    let formattedDate =
                        "-";

                    if (bill.billDate) {

                        const date =
                            new Date(
                                bill.billDate
                            );

                        if (
                            !isNaN(
                                date.getTime()
                            )
                        ) {

                            formattedDate =
                                date
                                .toLocaleDateString();
                        }
                    }

                    const row =
                        document.createElement(
                            "tr"
                        );

                    row.innerHTML = `
                        <td>
                            BILL-${String(
                                bill.billId
                            ).padStart(3, "0")}
                        </td>

                        <td>
                            ${appointmentNumber}
                        </td>

                        <td>
                            ${patientName}
                        </td>

                        <td>
                            Rs. ${Number(
                                bill.consultationFee
                            ).toLocaleString(
                                undefined,
                                {
                                    minimumFractionDigits: 2,
                                    maximumFractionDigits: 2
                                }
                            )}
                        </td>

                        <td>
                            Rs. ${Number(
                                bill.treatmentCost
                            ).toLocaleString(
                                undefined,
                                {
                                    minimumFractionDigits: 2,
                                    maximumFractionDigits: 2
                                }
                            )}
                        </td>

                        <td>
                            <strong>
                                Rs. ${Number(
                                    bill.totalAmount
                                ).toLocaleString(
                                    undefined,
                                    {
                                        minimumFractionDigits: 2,
                                        maximumFractionDigits: 2
                                    }
                                )}
                            </strong>
                        </td>

                        <td>
                            ${formattedDate}
                        </td>
                    `;

                    billsTableBody.appendChild(
                        row
                    );
                }
            );
        }
    }
);