package hello;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.Clock;
import java.util.logging.Logger;

@RestController
public class DemoController {

  private static final Logger logger = Logger.getLogger(DemoController.class.getName());

  @Autowired private DatastoreService datastoreService;
  @Autowired private ShaService shaService;
  @Autowired private FibonacciService fibonacciService;

  @RequestMapping("/visit/{greeting}")
  public String visit(@PathVariable String greeting) {

    Datastore datastore = datastoreService.getDatastore();

    String kind = "visit";
    long visitId = Clock.systemUTC().millis();
    Key visitKey = datastore.newKeyFactory().setKind(kind).newKey(visitId);
    String message = String.format("Welcome to Java Tools demo! Your visit number %d has been recorded", visitId);
    Entity visit = Entity.newBuilder(visitKey).set("message", message).build();
    datastore.put(visit);
    Entity savedVisit = datastore.get(visitKey);
    Translate translate = TranslateOptions.getDefaultInstance().getService();

    return translate.translate(savedVisit.getString("message"), Translate.TranslateOption.sourceLanguage("en"),
            Translate.TranslateOption.targetLanguage(translate.detect(greeting).getLanguage())).getTranslatedText();
  }

  @RequestMapping("/")
  public String index() {
    return "Hello World! v 6.0";
  }

  @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
  public String hello(@PathVariable String name, HttpServletRequest request) {
    String userAgent = request.getHeader("User-Agent");
    String displayName = "Browser";

    if (userAgent != null) {
      if (userAgent.contains("Mozilla")) {
        displayName = "Firefox";
      } else if (userAgent.contains("Explorer")) {
        displayName = "Internet Explorer";
      } else if (userAgent.contains("Safari")) {
        displayName = "Safari";
      } else if (userAgent.contains("Chrome")) {
        displayName = "Chrome";
      } else if (userAgent.contains("Opera")) {
        displayName = "Opera";
      } else {
        displayName = userAgent;
      }
    }
    return "Hi Mr or Mrs " + name + "!  You're using the browser " + displayName;
  }


  static final String HASH_PASS = "007";
  static final String HASH_FAIL = "0007";

  @RequestMapping("/slow")
  public String slow() {
    String result;
    do {
      result = shaService.getHashedTime();
      // artificial error
      if (result.startsWith(HASH_FAIL)) {
        throw new IllegalStateException(
                "Found an imposter hashed time (" + HASH_FAIL + ") : " + result);
      }
    } while (!result.startsWith(HASH_PASS));

    return "Found a James Bond hash (" + HASH_PASS + ") : " + result;
  }

  @RequestMapping("/fib/{n}")
  public String fibonacci(@PathVariable("n") int n) {
    return "Fibonacci : " + fibonacciService.fibonacci(n);
  }
}
