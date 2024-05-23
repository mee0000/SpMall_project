package com.example.spmall.pageController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PageController
 *
 * @author toy aa@nfda
 * @version 2024/05/06 3:30 PM
 * @since JDK11
 **/
@Controller
public class pageController {
    @RequestMapping("/front/{frontPage}")
    public String frontPage(@PathVariable String frontPage){
        return "/front/index.html";
    }

    @RequestMapping("/back")
    public String backPage(){
        return "/back/index.html";
    }

    @GetMapping("/error")
    public String redirectRoot() {
        return "/front/index";
    }
}
