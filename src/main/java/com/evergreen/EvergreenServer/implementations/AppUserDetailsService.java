package com.evergreen.EvergreenServer.implementations;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.evergreen.EvergreenServer.models.AppUser;
import com.evergreen.EvergreenServer.repositories.AppUserRepository;
import com.evergreen.EvergreenServer.security.dtos.CustomUserDetail;


@Component
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
        System.out.println("==============================");
        System.out.println("loadUserByUsername " + email);
        System.out.println("==============================");
        AppUser appUser = appUserRepository.findByEmail(email);
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
