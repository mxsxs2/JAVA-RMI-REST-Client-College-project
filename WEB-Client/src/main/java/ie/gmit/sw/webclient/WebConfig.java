package ie.gmit.sw.webclient;

import ie.gmit.sw.webclient.formatter.CarFormatter;
import ie.gmit.sw.webclient.formatter.LongToTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAutoConfiguration
@ComponentScan(value = {"ie.gmit.sw.webclient.formatter"})
public class WebConfig implements WebMvcConfigurer {

    @Autowired //Without autowire, this solution may not work
    private CarFormatter carFormatter;
    @Autowired
    private LongToTimeFormatter longToTimeFormatter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(carFormatter);
        registry.addFormatter(longToTimeFormatter);
    }
}
