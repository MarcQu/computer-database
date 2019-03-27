package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Menu")
public class Menu {
  private static final String VUE = "menu";
  @RequestMapping
  public String menu(Model model) {
      return VUE;
  }
}
