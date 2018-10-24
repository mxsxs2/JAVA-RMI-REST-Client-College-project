package ie.gmit.sw;

import java.util.Calendar;
import java.util.Date;

public class JAXBDateConverter {
    public static java.util.Date parse(String xmlDateTime) {
        return javax.xml.bind.DatatypeConverter.parseDateTime(xmlDateTime).getTime();
    }

    public static String print(Date javaDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(javaDate.getTime());
        return javax.xml.bind.DatatypeConverter.printDateTime(calendar);
    }
}