package hello;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.time.Clock;

@RestController
public class HelloController {

  private static final Logger logger = Logger.getLogger(HelloController.class.getName());

  @Autowired
  private DatastoreService datastoreService;

  @RequestMapping("/visit")
  public String visit() {

    Datastore datastore = datastoreService.getDatastore();

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

  @RequestMapping("/")
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

  @Autowired
  private ShaService shaService;

  static final String HASH_PASS = "007";
  static final String HASH_FAIL = "0007";

  @RequestMapping("/slow")
  public String slow() {
    String result;
    do {
      result = shaService.getHashedTime();
      // artificial error
      if (result.startsWith(HASH_FAIL)) {
        throw new IllegalStateException("Found an imposter hashed time (" + HASH_FAIL + ") : " + result);
      }
    } while(!result.startsWith(HASH_PASS));

    return "Found a James Bond hash (" + HASH_PASS + ") : " + result;
  }

}
