package com.learning.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

// Provides users to the auth service. For simplicity, the users are hardcoded
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final List<AppUser> users = Arrays.asList(
                new AppUser(1, "abdullah", encoder.encode("12345"), "USER"),
                new AppUser(2, "admin", encoder.encode("12345"), "ADMIN")
        );

        for (var user : users) {
            if (user.username.equals(username)) {
                var grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + user.getRole());
                return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
            }
        }

        throw new UsernameNotFoundException("Username: " + username + " not found");
    }

    private static class AppUser {
        private Integer id;
        private String username;
        private String password;
        private String role;

        public AppUser(Integer id, String username, String password, String role) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
