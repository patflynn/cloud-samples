package hello;

import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class FibonacciService {

  public BigInteger fibonacci(int n) {
    if (n == 0 || n == 1) {
      return BigInteger.valueOf(n);
    }
    return fibonacci(n - 2).add(fibonacci(n - 1));
  }
}
