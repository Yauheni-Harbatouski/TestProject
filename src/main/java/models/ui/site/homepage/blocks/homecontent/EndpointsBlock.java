package models.ui.site.homepage.blocks.homecontent;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static models.ui.site.homepage.blocks.homecontent.EndpointsLocators.*;

/**
 * Block endpoints
 */
public class EndpointsBlock {
    private final ElementsCollection endpoints = $$(ENDPOINTS);
    private final SelenideElement responseStatus = $(RESPONSE_STATUS);
    private final SelenideElement activeEndpoint = $(ACTIVE_ENDPOINT);
    private final SelenideElement responseBlock = $(RESPONSE_BLOCK);


    public SelenideElement getResponseBlock() {
        return responseBlock;
    }

    public SelenideElement getActiveEndpoint() {
        return activeEndpoint;
    }

    public SelenideElement getResponseStatus() {
        return responseStatus;
    }

    public ElementsCollection getEndpoints() {
        return endpoints;
    }
}
