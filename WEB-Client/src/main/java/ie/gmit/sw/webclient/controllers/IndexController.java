package ie.gmit.sw.webclient.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController implements ErrorController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }
    @RequestMapping("/error")
    public String handleError() {
        return "index";
    }
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
