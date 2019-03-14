package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/DeleteComputer")
public class DeleteComputer {
  @RequestMapping
  public String deleteComputer(Model model) {
      return "deleteComputer";
  }
}
