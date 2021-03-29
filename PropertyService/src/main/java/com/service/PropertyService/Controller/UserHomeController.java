package com.service.PropertyService.Controller;

import com.service.PropertyService.Service.UserService;
import com.service.PropertyService.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
public class UserHomeController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signUpForm(Model model) {
        model.addAttribute("form", new UserDto());
        return "signup";
    }

    @PostMapping("/user")
    public String signup(@Valid @ModelAttribute("form") UserDto form,
                         BindingResult result) {

        if (result.hasErrors()) {
            return "signup";
        }
        userService.join(form);
        return "redirect:/login";
    }

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request,
                             HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

    @GetMapping(value = "/")
    public String mainPage() {
        return "main";
    }

}
