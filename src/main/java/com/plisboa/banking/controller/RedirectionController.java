package com.plisboa.banking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RedirectionController {

  @GetMapping("/")
  public RedirectView redirectToApiDocs() {
    return new RedirectView("/swagger-ui/index.html#/");
  }
}

