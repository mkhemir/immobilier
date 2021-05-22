package com.immoapp.authentication.service;

import com.immoapp.authentication.model.CustomUser;
import com.immoapp.authentication.model.UserEntity;
import com.immoapp.authentication.repository.OAuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    OAuthRepo oauthDAOService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = null;

        try {
            userEntity = oauthDAOService.getUserDetails(username);

            if (userEntity != null && userEntity.getId() != null && !"".equalsIgnoreCase(userEntity.getId())) {
                CustomUser customUser = new CustomUser(userEntity);
                return customUser;
            } else {
                throw new UsernameNotFoundException("User " + username + " was not found in the database");
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

    }

}
