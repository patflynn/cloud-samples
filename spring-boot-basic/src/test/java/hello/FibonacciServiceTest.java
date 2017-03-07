package hello;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

public class FibonacciServiceTest {

  @Test
  public void testFibonacci36() {
    BigInteger x = new FibonacciService().fibonacci35();
    Assert.assertEquals(x.toString(), "14930352");
  }

}
