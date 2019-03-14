package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/DeleteCompany")
public class DeleteCompany {
  @RequestMapping
  public String deleteCompany(Model model) {
      return "deleteCompany";
  }
}
