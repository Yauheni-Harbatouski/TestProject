package core.configs.basetest;

import com.codeborne.selenide.Selenide;
import core.configs.SelenideConfiguration;
import core.configs.constants.Resolution;
import core.datasource.DataProviders;
import models.ui.logic.Navigation;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public abstract class BaseTest extends DataProviders {

    @BeforeClass(alwaysRun = true)
    protected void beforeMethod() {
        SelenideConfiguration.configure(Resolution.DESKTOP);
        Navigation.openHomePage();
    }

    @AfterClass(alwaysRun = true)
    protected void afterClass() {
        Selenide.closeWebDriver();
    }


}


