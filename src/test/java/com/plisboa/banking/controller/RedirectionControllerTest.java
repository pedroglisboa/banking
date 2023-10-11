package com.plisboa.banking.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.view.RedirectView;

class RedirectionControllerTest {

  private final RedirectionController redirectionController = new RedirectionController();

  @Test
  void redirectToApiDocs_ReturnsRedirectView() {
    RedirectView redirectView = redirectionController.redirectToApiDocs();
    assertThat(redirectView.getUrl()).isEqualTo("/swagger-ui/index.html#/");
  }

  @Test
  void redirectToApiDocs_RedirectsToCorrectPath() {
    RedirectView redirectView = redirectionController.redirectToApiDocs();
    assertThat(redirectView.isRedirectView()).isTrue();
  }
}

