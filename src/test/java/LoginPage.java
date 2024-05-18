import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class LoginPage extends PageBase {
    private By usernameLocator = By.name("username");
    private By passwordLocator = By.name("password");
    private By rememberLocator = By.name("remember");
    private By loginButtonLocator = By.className("submit");

    public LoginPage(WebDriver driver, PageBase prevPage) {
        super(driver, prevPage);
    }

    public PageBase login(String username, String password, boolean remember) {
        if (!remember) {
            this.waitAndReturnElement(rememberLocator).click();
        }

        this.waitAndReturnElement(usernameLocator).sendKeys(username);
        this.waitAndReturnElement(passwordLocator).sendKeys(password);
        this.waitAndReturnElement(loginButtonLocator).click();

        return prevPage;
    }
}
