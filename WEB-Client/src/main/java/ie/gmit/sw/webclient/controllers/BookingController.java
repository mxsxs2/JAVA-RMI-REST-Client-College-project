package ie.gmit.sw.webclient.controllers;

import ie.gmit.sw.model.BookingTimeFrame;
import ie.gmit.sw.webclient.dao.CarDAO;
import ie.gmit.sw.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Value("${spring.data.rest.base-path}")
    private String restURI;

    @Autowired
    private CarDAO carDAO;

    @GetMapping("/new")
    public String welcome(ModelMap model) {
        BookingTimeFrame btf = new BookingTimeFrame();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE,2);
        btf.setBookingTimeFrom(new Date().getTime());
        btf.setBookingTimeTo(c.getTime().getTime());

        Booking b= new Booking();
        b.setBookingTimeFrame(btf);

        model.put("message", "Create ");
        model.put("booking",b);
        model.put("cars", carDAO.getCars());
        return "booking/new";
    }
    @PostMapping("/new")
    public String welcome(@ModelAttribute @Valid Booking booking, BindingResult bindingResult, ModelMap model){
        model.put("message", "Create ");
        System.out.println(booking.getPerson().getFirstname());
        System.out.println(booking.getPerson().getAddress().getCity());
        System.out.println(booking.getCar().getId());
        System.out.println(booking.getBookingTimeFrame().getBookingTimeFrom());
        System.out.println(booking.getBookingTimeFrame().getBookingTimeTo());
        if (bindingResult.hasErrors()){
            return "booking/new";
        } else {

            return "booking/new";
        }
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