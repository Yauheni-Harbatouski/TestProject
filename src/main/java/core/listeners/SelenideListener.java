package core.listeners;

import com.codeborne.selenide.WebDriverRunner;
import core.configs.SelenideConfiguration;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class SelenideListener implements ITestListener {

    private static void addEnvironmentToReport() {
        try {
            Properties properties = new Properties();
            properties.setProperty("Application URL: ", WebDriverRunner.getWebDriver().getCurrentUrl());
            OutputStream out = new FileOutputStream(new File("target/allure-results/environment.properties"));
            properties.store(out, "Comment string");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart(ITestContext context) {
        SelenideConfiguration.configure();
    }

    @Override
    public void onFinish(ITestContext context) {
        addEnvironmentToReport();
    }

    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onTestFailure(ITestResult result) {
        attachImage();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        attachImage();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        attachImage();
    }

    @SuppressWarnings("UnusedReturnValue")
    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "png")
    private byte[] attachImage() {
        try {
            if (WebDriverRunner.hasWebDriverStarted()) {
                return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
            } else {
                return null;
            }
        } catch (WebDriverException e) {
            e.printStackTrace();
            return null;
        }
    }
}
