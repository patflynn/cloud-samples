package hello;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import org.springframework.stereotype.Component;

@Component
public class DatastoreService {

  private final Datastore datastore;

  public DatastoreService() {
    datastore = DatastoreOptions.getDefaultInstance().getService();
  }

  public Datastore getDatastore() {
    return datastore;
  }
}
