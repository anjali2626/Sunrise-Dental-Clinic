document.addEventListener("DOMContentLoaded", function () {

    const consultation =
        document.getElementById("consultationFee");

    const treatment =
        document.getElementById("treatmentCost");

    const total =
        document.getElementById("totalAmount");

    function calculateTotal() {

        const consultationValue =
            parseFloat(consultation.value) || 0;

        const treatmentValue =
            parseFloat(treatment.value) || 0;

        total.value =
            (consultationValue + treatmentValue).toFixed(2);
    }

    if (consultation && treatment) {

        consultation.addEventListener(
            "input",
            calculateTotal
        );

        treatment.addEventListener(
            "input",
            calculateTotal
        );
    }

    const form =
        document.getElementById("billingForm");

    if (form) {

        form.addEventListener("submit", function (event) {

            event.preventDefault();

            alert(
                "Billing form is working.\n\n" +
                "Backend API connection will be added later."
            );

        });
    }

});