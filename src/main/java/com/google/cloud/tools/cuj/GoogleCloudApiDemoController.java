package com.google.cloud.tools.cuj;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * TODO: Add Comments
 */
@Controller
public class GoogleCloudApiDemoController {

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @PostMapping("/translate")
  public String translate(Model model, String sourceText) {
    model.addAttribute(new TranslationResponse("Wagadoogoo", "You what!?"));
    return "index";
  }
}
