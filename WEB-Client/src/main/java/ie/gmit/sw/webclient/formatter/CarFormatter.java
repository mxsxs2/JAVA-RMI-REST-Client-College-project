package ie.gmit.sw.webclient.formatter;

import ie.gmit.sw.webclient.dao.CarDAO;
import ie.gmit.sw.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@ComponentScan(value = {"ie.gmit.sw.webclient.dao"})
public class CarFormatter implements Formatter<Car> {
        @Autowired
        CarDAO dao;

        @Override
        public String print(Car object, Locale locale) {
            return (object != null ? object.getId() : "");
        }

        @Override
        public Car parse(String text, Locale locale) throws ParseException {
            return this.dao.forId(text);
        }
}
