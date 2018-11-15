package com.google.cloud.tools.cuj;

/**
 * TODO: Add Comments
 */
public class TranslationResponse {
  public String sourceLanguage;
  public String translatedText;

  public TranslationResponse(String sourceLanguage, String translatedText) {
    this.sourceLanguage = sourceLanguage;
    this.translatedText = translatedText;
  }

  public String getSourceLanguage() {
    return sourceLanguage;
  }

  public void setSourceLanguage(String sourceLanguage) {
    this.sourceLanguage = sourceLanguage;
  }

  public String getTranslatedText() {
    return translatedText;
  }

  public void setTranslatedText(String translatedText) {
    this.translatedText = translatedText;
  }
}
