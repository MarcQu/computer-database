package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/UpdateComputer")
public class UpdateComputer {
  @RequestMapping
  public String updateComputer(Model model) {
      return "updateComputer";
  }
}
