package Spring.domain.login.controller;

import Spring.domain.login.dto.RegisterRequest;
import Spring.domain.login.entity.user.Grade;
import Spring.domain.login.entity.user.User;
import Spring.domain.login.service.UserService;
import Spring.domain.login.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final int REFRESH_TOKEN_EXPIRES = 60 * 60 * 24; // 1일

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("userid") String userid, @RequestParam("password") String password) {
        User userExists = userService.findUser(userid, password);
        if (userExists != null) {
            return "redirect:/home";
        } else {

            return "/login";
        }
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("registerForm") RegisterRequest registerRequest) {
        final boolean isRegistered = userService.signup(registerRequest);
        if (isRegistered) {
            return "redirect:/home";
        } else {
            return "/signup";
        }
    }
}
