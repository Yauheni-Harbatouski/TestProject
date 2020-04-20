package core.configs;

import com.codeborne.selenide.Configuration;
import core.configs.constants.Resolution;

import static com.codeborne.selenide.Browsers.CHROME;

public class SelenideConfiguration {
    private static Resolution resolution = Resolution.DESKTOP;



    public static void configure() {
        doAll();
    }

    public static void configure(Resolution resolution) {
        SelenideConfiguration.resolution = resolution;
        doAll();
    }

    private static void doAll() {
        doBasic();
        doResolution();
    }

    private static void doBasic() {
        Configuration.browser = CHROME;
        Configuration.fastSetValue = true;
        Configuration.savePageSource = false;
        Configuration.timeout = 30000;
        Configuration.reportsFolder = "target/allure-results";
    }

    private static void doResolution() {
        switch (resolution) {
            case DESKTOP:
                Configuration.browserSize = Resolution.DESKTOP.resolution();
                break;
            case TABLET:
                Configuration.browserSize = Resolution.TABLET.resolution();
                break;
            case MOBILE:
                Configuration.browserCapabilities = Configuration.browserCapabilities.merge(DriverConfiguration.getMobile());
                break;
            default:
                throw new IllegalArgumentException("Unsupported resolution");
        }
    }


}
