package hello;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

  private static final Logger logger = Logger.getLogger(HelloController.class.getName());

  static {
    System.out.println("my life");
  }
  static Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
  static int i = 1;

  @RequestMapping("/")
  public String index() {
    String kind = "visit";
    Key visitKey = datastore.newKeyFactory().setKind(kind).newKey(i++);
    String message = String.format("Visit #%d occurred", i);
    Entity visit = Entity.newBuilder(visitKey).set("message", message).build();
    datastore.put(visit);
    logger.info(String.format("visit #%d saved", visitKey.getId()));

    Entity savedVisit = datastore.get(visitKey);
    return "Greetings from Spring Boot!\n" + savedVisit.getString("message");
  }

}
