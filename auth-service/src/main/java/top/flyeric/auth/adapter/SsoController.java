package top.flyeric.auth.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController()
public class SsoController {

    @GetMapping("/sso/login")
    public ModelAndView loginPage(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView homePage(ModelAndView modelAndView) {
        modelAndView.setViewName("home");
        return modelAndView;
    }

}
