package com.the_itach1.web.admin;

import com.the_itach1.po.User;
import com.the_itach1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class LoginControl {

    @Autowired
    private UserService userService;


    //进入登陆界面
    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    //根据传入的参数来验证是否登陆成功
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes) {
        //验证

        User user = userService.checkUser(username, password);
        if (user != null) {
            //设置session，并且转到后台index
            session.setAttribute("user", user);
            return "admin/index";
        } else {
            //添加错误信息
            attributes.addFlashAttribute("message","用户名或密码错误");
            //使用重定向，不要直接返回到login
            return "redirect:/admin";
        }
    }

    //注销
    @GetMapping("/logout")
    public String logout(HttpSession session)
    {
        //删除session,并重定向到登陆界面
        session.removeAttribute("user");

        return "redirect:/admin";
    }
}
