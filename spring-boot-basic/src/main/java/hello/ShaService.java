package hello;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

@Component
public class ShaService {

  private MessageDigest md;

  public ShaService() throws NoSuchAlgorithmException {
    md = MessageDigest.getInstance("SHA-256");
  }

  public String getHashedTime() {
    String time = String.valueOf(System.currentTimeMillis());
    byte[] hash = md.digest(time.getBytes());
    Formatter formatter = new Formatter();
    for (byte b : hash) {
      formatter.format("%02x", b);
    }
    return formatter.toString();
  }

}
