package hello;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.Clock;
import java.util.logging.Logger;

@RestController
public class HelloController {

  private static final Logger logger = Logger.getLogger(HelloController.class.getName());

  private static Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

  @RequestMapping("/visit")
  public String visit() {
    String kind = "visit";
    long visitId = Clock.systemUTC().millis();
    Key visitKey = datastore.newKeyFactory().setKind(kind).newKey(visitId);
    String message = String.format("Visit #%d occurred", visitId);
    Entity visit = Entity.newBuilder(visitKey).set("message", message).build();
    datastore.put(visit);
    logger.info(String.format("visit #%d saved", visitKey.getId()));

    Entity savedVisit = datastore.get(visitKey);
    return "Greetings from Spring Boot!\n" + savedVisit.getString("message");
  }

  @RequestMapping("/get")
  public String index() {
    return "Hello World!";
  }


  @RequestMapping(value="/hello/{name}", method = RequestMethod.GET)
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

}
