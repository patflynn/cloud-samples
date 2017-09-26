package com.google.cloud.tools.cuj;

import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
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
    Translate translate = TranslateOptions.getDefaultInstance().getService();

    Detection detectedSourceLanguage = translate.detect(sourceText);
    Translation translation =
        translate.translate(
            sourceText,
            TranslateOption.sourceLanguage(detectedSourceLanguage.getLanguage()),
            TranslateOption.targetLanguage("fa"));
    model.addAttribute(
        new TranslationResponse(detectedSourceLanguage.getLanguage(),
            translation.getTranslatedText()));
    return "index";
  }
}
