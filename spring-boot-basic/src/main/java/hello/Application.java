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
    ApplicationContext ctx = SpringApplication.run(Application.class, args);
  }

}
