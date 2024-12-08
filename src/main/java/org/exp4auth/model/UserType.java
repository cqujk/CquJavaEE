package org.exp4auth.model;

import lombok.Getter;

@Getter
public enum UserType {
    student("student"),
    teacher("teacher"),
    admin("admin");

    private final String type;

    UserType(String type) {
        this.type = type;
    }

    public static UserType fromString(String type) {
        for (UserType userType : UserType.values()) {
            if (userType.getType().equals(type)) {
                return userType;
            }
        }
        throw new IllegalArgumentException("Invalid user type: " + type);
    }

    public String toUpperCase() {
        return type.toUpperCase();
    }
}
