package com.bayaniact.util.enums;

public enum ApprovalStatus {
    FOR_APPROVAL("FOR APPROVAL"),
    APPROVED("APPROVED"),
    DECLINED("DECLINED");

    private final String value;

    ApprovalStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ApprovalStatus fromValue(String value) {
        for (ApprovalStatus status : ApprovalStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        System.out.println("Invalid value for ApprovalStatus: '" + value + "'");  // Log invalid values
        throw new IllegalArgumentException("Invalid value for ApprovalStatus: " + value);
    }

    public String getDisplayName() {
        // Converts enum name to a more readable form and capitalizes the first letter
        String readableName = name().replace("_", " ").toLowerCase(); // Example: "FOR_APPROVAL" -> "for approval"
        return readableName.substring(0, 1).toUpperCase() + readableName.substring(1); // Capitalizes "for approval" -> "For approval"
    }
}