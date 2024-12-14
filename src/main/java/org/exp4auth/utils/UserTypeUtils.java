package org.exp4auth.utils;

import org.exp4auth.model.UserType;

public class UserTypeUtils {
    public static UserType getUserTypeFromUserId(String userId) {
        if (userId == null || userId.isEmpty()) {
            return null;
        }

        if (userId.matches("1\\d{4}\\d+")) {
            return UserType.teacher;
        } else if (userId.matches("3\\d{4}\\d+")) {
            return UserType.admin;
        } else if (userId.matches("\\d{4}\\d+")) {
            return UserType.student;
        } else {
            return null;
        }
    }
}
