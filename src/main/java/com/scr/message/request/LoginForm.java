package com.scr.message.request;

import javax.validation.constraints.NotBlank;

public class LoginForm {
    
    private String email;
    
    private String userName;

    @NotBlank
    //@Size(min = 6, max = 40)
    private String password;
  

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public String toString() {
		return "LoginForm [email=" + email + ", userName=" + userName + ", password=" + password + "]";
	}
    
    
}