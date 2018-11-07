package ie.gmit.sw.webclient.controllers;

import ie.gmit.sw.model.Booking;
import ie.gmit.sw.model.BookingTimeFrame;
import ie.gmit.sw.model.Bookingmessage;
import ie.gmit.sw.model.Car;
import ie.gmit.sw.webclient.RestService;
import ie.gmit.sw.webclient.dao.BookingDAO;
import ie.gmit.sw.webclient.dao.CarDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private CarDAO carDAO;
    @Autowired
    private BookingDAO bookingDAO;

    @GetMapping("/new")
    public String newBooking(ModelMap model) {
        //Init car list holder
        List<Car> c = new ArrayList<>();
        //Create new date time frame  and pepopulate
        BookingTimeFrame btf = new BookingTimeFrame();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date());
        c1.add(Calendar.DATE, 1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());
        c2.add(Calendar.DATE, 2);
        btf.setBookingTimeFrom(c1.getTime().getTime());
        btf.setBookingTimeTo(c2.getTime().getTime());
        //Create new booking with the date tyme frame
        Booking b = new Booking();
        b.setBookingTimeFrame(btf);

        try {
            c = carDAO.getCars();
        } catch (Exception e) {
            if (RestService.isServerAway(e.getMessage())) {
                model.put("noserver", true);
            }
        }


        model.put("message", "Create ");
        model.put("booking", b);
        model.put("cars", c);
        return "booking/new";
    }

    @PostMapping("/new")
    public String newBooking(@RequestParam("modify") String modify, @Valid @ModelAttribute("booking") Booking booking, BindingResult bindingResult, ModelMap model) {
        //Init car list holder
        List<Car> c = new ArrayList<>();
        model.put("message", "Create ");
        //Get current time
        long now = new Date().getTime();

        //If the booking from is in past
        if (booking.getBookingTimeFrame().getBookingTimeFrom() <= now)
            bindingResult.rejectValue("bookingTimeFrame.bookingTimeFrom", "time.inpast", "Should be in future");
        //If booking to is in past
        if (booking.getBookingTimeFrame().getBookingTimeTo() <= now)
            bindingResult.rejectValue("bookingTimeFrame.bookingTimeTo", "time.inpast", "Should be in future");
        //If booking to is before than from
        if (booking.getBookingTimeFrame().getBookingTimeTo() <= booking.getBookingTimeFrame().getBookingTimeFrom())
            bindingResult.rejectValue("bookingTimeFrame.bookingTimeTo", "time.inpast", "Should be after collection date");
        //Set the current reservation time
        booking.setReservationTime(now);
        //Check if it is a modify operation and set appropriate model key value pairs
        if (modify != null && modify.equals("true")) {
            model.put("message", "Modify ");
            model.put("modify", true);
        }

        //Try to get Car list
        try {
            c = carDAO.getCars();
        } catch (Exception e) {
            if (RestService.isServerAway(e.getMessage())) {
                model.put("noserver", true);
            }
        }
        model.put("cars", c);


        //If there are any errors then just show the form again
        if (bindingResult.hasErrors()) {
            /*bindingResult.getAllErrors().forEach(e -> {
                System.out.println(e.getDefaultMessage());
                System.out.println(e.toString());
            });*/
            model.put("booking", booking);
            return "booking/new";
        } else {

            Bookingmessage bs = null;
            //Try to modify/save the booking
            try {
                if (modify != null && modify.equals("true")) {
                    bs = (Bookingmessage) bookingDAO.change(booking);
                } else {
                    bs = (Bookingmessage) bookingDAO.save(booking);
                }
            } catch (Exception e) {
                if (RestService.isServerAway(e.getMessage())) {
                    model.put("noserver", true);
                }
            }
            //If the success
            if (bs != null && bs.getBooking() != null && bs.getMessage().equals("ok")) {
                //Go to view page
                return "redirect:/booking/view/" + bs.getBooking().getId();
            } else {
                //Show the form again
                model.put("couldnotsave", true);
                if (bs != null && bs.getMessage() != null && bs.getMessage().equals("carnotavailable")) {
                    model.put("bookingmessage", "This car is not available for this renting period");
                }

                return "booking/new";
            }
        }
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") String id, ModelMap model) {
        Booking b = null;
        //Try to get the booking
        try {
            b = bookingDAO.forId(id);
        } catch (Exception e) {
            if (RestService.isServerAway(e.getMessage())) {
                model.put("noserver", true);
            }
        }
        //If success set the booking otherwise the not found message
        if (b != null) {
            model.put("booking", b);
        } else {
            model.put("notfound", id);
        }

        model.put("message", "");
        return "booking/view";
    }

    @GetMapping("/view")
    public String view(ModelMap model) {
        model.put("message", "Find ");
        return "booking/view";
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") String id, ModelMap model) {
        Booking b = null;
        List<Car> c = new ArrayList<>();
        //Try to get booking and the cars list
        try {
            b = bookingDAO.forId(id);
            c = carDAO.getCars();
        } catch (Exception e) {
            if (RestService.isServerAway(e.getMessage())) {
                model.put("noserver", true);
            }
        }
        //If there is a booking set the key value pairs and show form otherwise show not found message
        if (b != null) {
            model.put("message", "Modify ");
            model.put("booking", b);
            model.put("modify", true);
            model.put("cars", c);
            return "booking/new";
        } else {
            model.put("notfound", id);
            model.put("message", "Find ");
            return "booking/view";
        }
    }

    @GetMapping("/modify")
    public String modify(ModelMap model) {
        model.put("message", "Modify ");
        model.put("modify", true);
        return "forward:/booking/view";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, ModelMap model) {
        //Init variables
        Booking b = null;
        boolean deleted = false;
        //Try to delete the booking and try to get it again to confirm its deleted
        try {
            //Delete
            deleted = bookingDAO.delete(id);
            //Try to get it again to confirm
            b = bookingDAO.forId(id);
        } catch (Exception e) {
            if (RestService.isServerAway(e.getMessage())) {
                model.put("noserver", true);
            }
        }

        model.put("id", id);
        model.put("message", "");
        model.put("deleted", deleted);
        if (b != null) {
            model.put("booking", b);
            model.put("deleted", false);
        }

        return "booking/view";
    }


}