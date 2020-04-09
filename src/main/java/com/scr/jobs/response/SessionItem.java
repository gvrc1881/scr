package com.scr.jobs.response;

import lombok.*;
import java.util.*;

@Data
public class SessionItem {
    private String  token;
    private String  userId;
    private String  firstName;
    private String  lastName;
    private String  email;
    private List<String> roles;
}
