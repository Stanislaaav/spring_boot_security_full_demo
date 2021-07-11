package com.example.demo.enums;

public enum UserPermissionEnum {

    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");

    private final String permission;

    UserPermissionEnum(String permissiion) {
        this.permission = permissiion;
    }

    public String getPermission() {
        return permission;
    }
}
