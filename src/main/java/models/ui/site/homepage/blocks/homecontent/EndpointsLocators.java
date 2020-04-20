package models.ui.site.homepage.blocks.homecontent;

import org.openqa.selenium.By;

class EndpointsLocators {
    static final By ENDPOINTS = By.cssSelector(".endpoints li");
    static final By RESPONSE_STATUS = By.cssSelector(".response-code");
    static final By ACTIVE_ENDPOINT = By.cssSelector("[data-key='endpoint'].active");
    static final By RESPONSE_BLOCK = By.cssSelector("[data-key='output-response']");
}
