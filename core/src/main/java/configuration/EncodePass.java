package configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncodePass {

  public static void main(String[] args) {
    String encoded=new BCryptPasswordEncoder().encode("admin");
    System.out.println(encoded);
  }

}
