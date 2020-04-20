package core.datasource;

import org.testng.annotations.DataProvider;

/**
 * Тестовые данные
 */
public class DataProviders {
    @DataProvider(name = "endpoint name and response")
    public Object[][] dataProviderMethod() {
        return new Object[][]
                {{"LIST USERS", "200"},
                        {"SINGLE USER", "200"},
                        {"SINGLE USER NOT FOUND", "404"},
                        {"LIST <RESOURCE>", "200"},
                        {"SINGLE <RESOURCE>", "200"},
                        {"SINGLE <RESOURCE> NOT FOUND", "404"},
                        {"CREATE", "201"},
                        {"UPDATE", "200"},
                        {"DELETE", "204"},
                        {"REGISTER - SUCCESSFUL", "200"},
                        {"REGISTER - UNSUCCESSFUL", "400"},
                        {"LOGIN - SUCCESSFUL", "200"},
                        {"LOGIN - UNSUCCESSFUL", "400"},
                        {"DELAYED RESPONSE", "200"}};
    }
}
