package com.evergreen.EvergreenAuthServer.implementations;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.evergreen.EvergreenAuthServer.models.AppUserModel;
import com.evergreen.EvergreenAuthServer.repositories.AppUserRepository;
import com.evergreen.EvergreenAuthServer.security.dtos.CustomUserDetail;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class AppUserDetailsService implements UserDetailsService {

    AppUserRepository appUserRepository;

    public AppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER")); // example
    }

    @Override
    public CustomUserDetail loadUserByUsername(String email) throws UsernameNotFoundException {

        AppUserModel appUser = appUserRepository.findByEmail(email);
        log.info("user found for email {}", appUser);
        if (appUser == null) {
            // It only expects UsernameNotFoundException to be thrown from here
            throw new UsernameNotFoundException("User not found.");
        }
        CustomUserDetail userDetails = new CustomUserDetail();
        userDetails.setUsername(appUser.getEmail());
        userDetails.setPassword(appUser.getPassword());
        return userDetails;
    }


}
