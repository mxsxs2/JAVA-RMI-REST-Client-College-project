package ie.gmit.sw.webclient.formatter;

import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
public class LongToTimeFormatter implements Formatter<Long> {

    @Override
    public String print(Long d, Locale locale) {
        //Get formatter
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd H:m");
        //Set the current time if the time is null
        if (d == null) d = new Date().getTime();
        //Return Formatted Time
        return sdf.format(new Date(d));
    }

    @Override
    public Long parse(String d, Locale locale) throws ParseException {

        return new Date(d).getTime();
    }
}
