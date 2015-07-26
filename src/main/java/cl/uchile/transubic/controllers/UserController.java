package cl.uchile.transubic.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.uchile.transubic.service.UserService;
import cl.uchile.transubic.user.model.User;

@Controller
@RequestMapping(value = { "/user" })
public class UserController {

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@RequestMapping(value = { "/formCrearUsuario" }, method = RequestMethod.GET)
	public String addUserForm(Model model) {
		
		User user = new User();
		
		model.addAttribute("user", user);
		model.addAttribute("breadcrumbP", "Usuario");
		model.addAttribute("breadcrumbCh", "crear");

		return "users/crearUsuario";
	}

	@RequestMapping(value = { "/crearUsuario" }, method = RequestMethod.POST)
	public String addUser(@Valid User user, BindingResult bindingResult,
			Model model,
			final RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("user", user);
			model.addAttribute("breadcrumbP", "Usuario");
			model.addAttribute("breadcrumbCh", "crear");
			return "users/crearUsuario";
		}

		this.userService.addUser(user);
		
		redirectAttributes.addFlashAttribute("formMessage",
				"Usuario agregado exitosamente.");

		return "redirect:/login/";
	}
}