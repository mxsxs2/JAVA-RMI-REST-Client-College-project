package ie.gmit.sw.webclient.controllers;

import ie.gmit.sw.model.Booking;
import ie.gmit.sw.model.BookingTimeFrame;
import ie.gmit.sw.webclient.dao.BookingDAO;
import ie.gmit.sw.webclient.dao.CarDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private BookingDAO bookingDAO;

    @GetMapping("/new")
    public String welcome(ModelMap model) {
        BookingTimeFrame btf = new BookingTimeFrame();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 2);
        btf.setBookingTimeFrom(new Date().getTime());
        btf.setBookingTimeTo(c.getTime().getTime());

        Booking b = new Booking();
        b.setBookingTimeFrame(btf);

        model.put("message", "Create ");
        model.put("booking", b);
        model.put("cars", carDAO.getCars());
        return "booking/new";
    }

    @PostMapping("/new")
    public String welcome(@Valid @ModelAttribute("booking") Booking booking, BindingResult bindingResult, ModelMap model) {
        model.put("message", "Create ");
        model.put("cars", carDAO.getCars());
        Long now = new Date().getTime();

        if (booking.getBookingTimeFrame().getBookingTimeFrom() <= now)
            bindingResult.rejectValue("bookingTimeFrame.bookingTimeFrom", "time.inpast", "Should be in future");
        if (booking.getBookingTimeFrame().getBookingTimeTo() <= now)
            bindingResult.rejectValue("bookingTimeFrame.bookingTimeTo", "time.inpast", "Should be in future");
        if (booking.getBookingTimeFrame().getBookingTimeTo() <= booking.getBookingTimeFrame().getBookingTimeFrom())
            bindingResult.rejectValue("bookingTimeFrame.bookingTimeTo", "time.inpast", "Should be after collection date");

        booking.setReservationTime(now);

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(e -> {
                System.out.println(e.getDefaultMessage());
                System.out.println(e.toString());
            });
            System.out.println("errors");
            return "booking/new";
        } else {
            Booking b = bookingDAO.save(booking);
            if (b != null) {
                return "redirect:/booking/view/" + b.getId();
            } else {
                model.put("couldnotsave", true);
                return "booking/new";
            }
        }
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") String id, ModelMap model) {
        Booking b = bookingDAO.forId(id);
        if (b != null) {
            model.put("booking", b);
        } else {
            model.put("notfound", id);
        }
        model.put("message", "");
        return "booking/view";
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