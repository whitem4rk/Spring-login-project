package Spring.login.controller;

import Spring.login.domain.user.Grade;
import Spring.login.domain.user.User;
import Spring.login.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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
            return "redirect:/login";
        }
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam("username") String username, @RequestParam("userid") String userid,
            @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        User userExists = userService.findUser(userid);
        if (userExists != null) {
            return "signup";
        } else {
            User user = new User(null, username, userid, password, Grade.CLIENT);
            userService.signup(user);
            return "redirect:/login";
        }
    }
}
