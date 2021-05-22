package com.immoapp.authentication.repository;

import com.immoapp.authentication.model.UserEntity;

public interface OAuthRepo {

    public UserEntity getUserDetails(String emailId);
}
