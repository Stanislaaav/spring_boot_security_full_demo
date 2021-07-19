package com.example.demo.auth;

import static com.example.demo.enums.UserRoleEnum.ADMIN;
import static com.example.demo.enums.UserRoleEnum.STUDENT;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class FakeApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return Optional.empty();
    }

    private List<UserDetails> getUserDetails() {
        List<UserDetails> applicationUsers = Lists.newArrayList(
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
            ),
        );

        return applicationUsers;
    }
}
