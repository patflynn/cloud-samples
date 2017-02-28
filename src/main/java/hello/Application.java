package hello;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

  public static void main(String[] args) throws Exception {
    InputStream in = Application.class.getClassLoader().getResourceAsStream("source-context.json");
    ApplicationContext ctx = SpringApplication.run(Application.class, args);
    if (in != null) {
      System.out.println("Found source context.");
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
    } else {
      System.out.println("source context not found.");
    }

    System.out.println("Let's inspect the beans provided by Spring Boot:");

    String[] beanNames = ctx.getBeanDefinitionNames();
    Arrays.sort(beanNames);
    for (String beanName : beanNames) {
      System.out.println(beanName);
    }
  }

}
