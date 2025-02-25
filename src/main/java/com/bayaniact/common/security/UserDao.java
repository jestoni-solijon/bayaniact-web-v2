package com.bayaniact.common.security;

import com.bayaniact.common.entity.User;

public interface UserDao {

    User findByUserName(String userName);

    User findByUserUuid(String userUUID);

    void save(User theUser);
}
