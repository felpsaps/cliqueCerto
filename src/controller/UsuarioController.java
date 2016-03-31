package controller;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import model.UsuarioBean;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import DAO.UsuarioDAO;

@Controller
public class UsuarioController {
	
	@RequestMapping (value="/jsp/login.do", method = RequestMethod.POST)
	public String login(@RequestParam(required=false,value="email") String email,
						@RequestParam(required=false,value="pass") String pass,
						HttpSession session) {
		
		try {
			UsuarioBean usr = new UsuarioDAO().login(email, pass);
			if (usr != null) {
				session.setAttribute("usr", usr);
				session.setMaxInactiveInterval(Integer.MAX_VALUE);
				return "/index";
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return "/index";
	}
	
	@RequestMapping (value="/jsp/logout.do", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "/index";
	}
	
	@RequestMapping (value="/jsp/insere.do", method = RequestMethod.POST)
	public String insere(UsuarioBean usr) {
		try {
			UsuarioDAO dao = new UsuarioDAO();
			dao.insert(usr);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return "/index";
	}
	
}
