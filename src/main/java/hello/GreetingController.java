package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
    @RequestMapping("/celsiusToFahrenheit")
    public ResponseEntity<?>  celsiusToFahrenheit(@RequestParam(value="celsius", defaultValue="39.5") float celsius) {
    	
    	float fahrenheit;
        System.out.println("Enter temperatue in Celsius");
             
        fahrenheit = 32 + (celsius * 9 / 5);
     
        System.out.println("Temperature in Fahrenheit = " + fahrenheit);
        
        Message message = new Message(celsius +" ºC = " + fahrenheit + " ºF");
        
    	return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    

}
