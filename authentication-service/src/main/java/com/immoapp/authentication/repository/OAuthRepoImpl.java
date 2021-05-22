package com.immoapp.authentication.repository;

import com.immoapp.authentication.model.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OAuthRepoImpl implements OAuthRepo{

    @Override
    public UserEntity getUserDetails(String emailId) {

        Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();

        List<UserEntity> users = new ArrayList<>();
        UserEntity user1 = new UserEntity();
        user1.setEmailId("john@gmail.com");
        user1.setId("1");
        user1.setName("John");
        user1.setPassword("$2a$10$jbIi/RIYNm5xAW9M7IaE5.WPw6BZgD8wcpkZUg0jm8RHPtdfDcMgm");
        UserEntity user2 = new UserEntity();
        user2.setEmailId("mike@gmail.com");
        user2.setId("2");
        user2.setName("Mike");
        user2.setPassword("$2a$10$jbIi/RIYNm5xAW9M7IaE5.WPw6BZgD8wcpkZUg0jm8RHPtdfDcMgm");
        users.add(user1);
        users.add(user2);
        List<String> permissions = new ArrayList<>();
        permissions.add("ROLE_CREATE_NOTE");
        permissions.add("ROLE_EDIT_NOTE");
        permissions.add("ROLE_VIEW_ALL_NOTE");


        List<UserEntity> list = users;

        if(!list.isEmpty()) {

            UserEntity userEntity = list.stream().filter(p -> emailId.equals(p.getEmailId())).findAny().get();



            if (permissions != null && !permissions.isEmpty()) {
                for (String permission : permissions) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission);
                    grantedAuthoritiesList.add(grantedAuthority);
                }
                userEntity.setGrantedAuthoritiesList(grantedAuthoritiesList);
            }
            return userEntity;
        }

        return null;



    }
}
