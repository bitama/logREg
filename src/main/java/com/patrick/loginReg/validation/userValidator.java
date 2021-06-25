package com.patrick.loginReg.validation;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.patrick.loginReg.models.User;
import com.patrick.loginReg.services.UserService;
@Component
public class userValidator implements Validator{
	private final UserService userService;
	
	 public userValidator(UserService userService) {
		super();
		this.userService = userService;
	}

	@Override
	    public boolean supports(Class<?> clazz) {
	        return User.class.equals(clazz);
	    }
	    
	    // 2
	    @Override
	    public void validate(Object object, Errors errors) {
	        User user = (User) object;
	        
	        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
	            // 3
	            errors.rejectValue("passwordConfirmation", "Match");
	        } 
	        if(userService.findByEmail(user.getEmail())!=null) {
//	        	errors.rejectValue("email", "DupeEmail");
	        }
	    }
}
