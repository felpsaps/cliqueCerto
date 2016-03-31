package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UsuarioBOController {

	@RequestMapping (value="/jsp/login2.do", method = RequestMethod.POST)
	public String login() {
		return "/index";
	}
}
