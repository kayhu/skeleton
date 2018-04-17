import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class QuickTest {

  public static void main(String[] args) {
    System.out.println(new BCryptPasswordEncoder().encode("Hk123456"));
  }
}
