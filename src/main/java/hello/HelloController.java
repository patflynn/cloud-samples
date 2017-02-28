package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {
  static {
    System.out.println("my life");
  }
  @RequestMapping("/")
  public String index() {
    int i = 0;
    return "Greetings from Spring Boot!";
  }

}
