package Spring.domain.login.controller;

import Spring.domain.login.dto.LoginRequest;
import Spring.domain.login.dto.RegisterRequest;
import Spring.domain.login.dto.UpdatePasswordRequest;
import Spring.domain.login.entity.user.User;
import Spring.domain.login.exception.FilterMustResponseException;
import Spring.domain.login.exception.JwtInvalidException;
import Spring.domain.login.service.UserService;
import javax.servlet.http.Cookie;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @Value("${refresh-token-expires}")
    private final int REFRESH_TOKEN_EXPIRES;

    @GetMapping("/home")
    public String home(@ModelAttribute("username") String username, Model model) {
        model.addAttribute("username", username);
        return "/home";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginRequest loginRequest, RedirectAttributes redirectAttributes) {
        throw new FilterMustResponseException();
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("registerForm") RegisterRequest registerRequest) {
        final boolean isRegistered = userService.signup(registerRequest);
        if (isRegistered) {
            return "redirect:/login";
        } else {
            return "/signup";
        }
    }

    @PostMapping("/reissue")
    public String reissue(@CookieValue(value = "refreshToken", required = false) Cookie refreshCookie) {
        throw new JwtInvalidException();
    }

    @PutMapping("/updatepw")
    public String updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        userService.changePassword(updatePasswordRequest);

        return "redirect:/login";
    }
}
