package com.personalprojects.clockcheck.home

object Validation {

    fun validateInput(input: String): ValidationResult {
        val numberPattern = Regex("^\\d+\$")
        val greaterThan24 = Regex("^(?!2[5-9]|[3-9]\\d|\\d{3,})\\d+\$")

        return if (input.isEmpty()) {
            ValidationResult.EMPTY_INPUT
        } else if (input.length > 2) {
            ValidationResult.ERROR_LIMIT
        } else if (!input.matches(numberPattern)) {
            ValidationResult.ERROR_NOT_NUMBER
        } else if (!input.matches(greaterThan24)) {
            ValidationResult.ERROR_GREATER_THAN_24
        } else {
            ValidationResult.SUCCESS
        }
    }
}

enum class ValidationResult {
    SUCCESS,

    ERROR_LIMIT,

    EMPTY_INPUT,

    ERROR_NOT_NUMBER,

    ERROR_GREATER_THAN_24;
}
