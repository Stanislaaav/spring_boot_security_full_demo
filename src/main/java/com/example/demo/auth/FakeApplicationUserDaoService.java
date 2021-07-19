package com.example.demo.auth;

import static com.example.demo.enums.UserRoleEnum.ADMIN;
import static com.example.demo.enums.UserRoleEnum.STUDENT;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getUserDetails().stream()
            .filter(applicationUser -> username.equals(applicationUser.getUsername()))
            .findFirst();
    }

    private List<ApplicationUser> getUserDetails() {
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
            new ApplicationUser(
                "ana",
                passwordEncoder.encode("pass"),
                STUDENT.getGrantedAutorities(),
                true,
                true,
                true,
                true
            ),
            new ApplicationUser(
                "linda",
                passwordEncoder.encode("pass"),
                ADMIN.getGrantedAutorities(),
                true,
                true,
                true,
                true
            ),new ApplicationUser(
                "tom",
                passwordEncoder.encode("pass"),
                ADMIN.getGrantedAutorities(),
                true,
                true,
                true,
                true
            )
        );

        return applicationUsers;
    }
}
