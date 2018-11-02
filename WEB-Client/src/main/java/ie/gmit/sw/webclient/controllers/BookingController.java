package ie.gmit.sw.webclient.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @RequestMapping("/new")
    public String welcome(ModelMap model) {
        model.put("message", "Create ");
        return "booking/new";
    }
    @RequestMapping("/view")
    public String view(ModelMap model) {
        model.put("message", "");
        return "booking/view";
    }
    @RequestMapping("/modify")
    public String modify(ModelMap model) {
        model.put("message", "Modify ");
        return "booking/modify";
    }
    @RequestMapping("/delete")
    public String delete(ModelMap model) {
        model.put("message", "Delete ");
        return "booking/delete";
    }

}