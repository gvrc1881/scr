package com.scr.identity;

import org.springframework.security.core.authority.AuthorityUtils;

import com.scr.model.User;


public class TokenUser extends org.springframework.security.core.userdetails.User {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;

    //For returning a normal user
    public TokenUser(User user) {
        super( user.getId().toString(), user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_ADMIN")  );      
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
