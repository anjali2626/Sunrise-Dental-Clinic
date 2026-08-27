function validateRequired(value, fieldName) {

    if (!value || value.trim() === "") {
        return fieldName + " is required.";
    }

    return "";
}

function validatePhone(value) {

    if (!value || value.trim() === "") {
        return "Contact number is required.";
    }

    if (!/^[0-9+\-\s]{7,20}$/.test(value)) {
        return "Please enter a valid contact number.";
    }

    return "";
}

function validatePositiveNumber(value, fieldName) {

    const number = parseFloat(value);

    if (isNaN(number) || number < 0) {
        return fieldName + " must be a valid positive number.";
    }

    return "";
}