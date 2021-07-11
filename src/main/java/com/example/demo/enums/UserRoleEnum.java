package com.example.demo.enums;

import static com.example.demo.enums.UserPermissionEnum.COURSE_READ;
import static com.example.demo.enums.UserPermissionEnum.COURSE_WRITE;
import static com.example.demo.enums.UserPermissionEnum.STUDENT_READ;
import static com.example.demo.enums.UserPermissionEnum.STUDENT_WRITE;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRoleEnum {

    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ));

    private final Set<UserPermissionEnum> permissions;

    UserRoleEnum(Set<UserPermissionEnum> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermissionEnum> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAutorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissions;
    }
}
