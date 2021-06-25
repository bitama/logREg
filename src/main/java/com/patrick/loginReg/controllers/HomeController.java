package com.patrick.loginReg.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.patrick.loginReg.models.User;
import com.patrick.loginReg.services.UserService;
import com.patrick.loginReg.validation.userValidator;


@Controller
public class HomeController {
	private final UserService userServ;
	private final userValidator userValidator;

	public HomeController(UserService userServ,userValidator userValid) {
		super();
		this.userServ = userServ;
		this.userValidator=userValid;
	}
	
	@GetMapping("/")
	public String index(@ModelAttribute("user")User user) {
		return "index.jsp";
	}
	@RequestMapping(value="/registration", method=RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
		
		userValidator.validate(user,result);
		if(result.hasErrors()) {
			return"index.jsp";
		}
		User u =userServ.registerUser(user);
		session.setAttribute("userId", u.getId());
		return "redirect:/success";
        
    }
	@GetMapping("/success")
	public String success(Model model,HttpSession session) {
		Long id=(Long)session.getAttribute("userId");
		User loggedUser=userServ.findUserById(id);
		model.addAttribute("loggedUser",loggedUser);
		return "success.jsp";
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@PostMapping(value="/login")
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session,RedirectAttributes redirectAttributes) {
    	boolean isLegit= userServ.authenticateUser(email, password);
    	if(isLegit) {
    		User user = userServ.findByEmail(email);
    		session.setAttribute("userId",user.getId());
    		return "redirect:/success";
    	}
    	redirectAttributes.addFlashAttribute("error", "Invalid login attemped");
    	return "redirect:/";
    }
    

    
	
   
}
