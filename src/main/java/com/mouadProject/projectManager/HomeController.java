package com.mouadProject.projectManager;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping({"/",  "/{file:(?!bundle\\.js|api)\\w*}",  "/{file:(?!bundle\\.js|api)\\w*}/**" })
    public String landingPage(HttpServletRequest request) {
		System.out.println("request: "+request.getRequestURI());
		
		if(request.getRequestURI().endsWith(".jpg") || request.getRequestURI().endsWith(".png")|| request.getRequestURI().endsWith(".js")) {
			String[] req = request.getRequestURI().split("/");
			System.out.println("forward:" + "/" + req[req.length - 1]);
			return "forward:" + "/" + req[req.length - 1];
		}
        return "index";
    }
	
}
