package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Menu {
  @RequestMapping
  public String menu(Model model) {
      return "menu";
  }
}
