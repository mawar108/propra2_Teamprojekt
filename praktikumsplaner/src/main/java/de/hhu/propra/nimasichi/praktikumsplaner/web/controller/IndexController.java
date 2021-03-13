package de.hhu.propra.nimasichi.praktikumsplaner.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

  @GetMapping("/")
  public String handleGetIndex(
      @AuthenticationPrincipal final OAuth2User principal) {
    return "index";
  }

}
