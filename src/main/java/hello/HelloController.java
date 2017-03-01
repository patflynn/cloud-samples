package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {
  int i;
  static {
    System.out.println("my life");
  }
  @RequestMapping("/")
  public String index() {
    i++;
    System.out.println("request #" + i);
    return "Greetings from Spring Boot!";
  }
}
