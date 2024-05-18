import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

public class ProfilePage extends PageBase {
    private By userLocator = By.className("user-link");
    private By flashSuccessLocator = By.className("flash-success");

    public ProfilePage(WebDriver driver, PageBase prevPage) {
        super(driver, prevPage);
    }

    public String getProfileUsername() {
        return this.waitAndReturnElement(userLocator).getText();
    }

    public boolean isSuccess() {
        return !this.driver.findElements(flashSuccessLocator).isEmpty();
    }
}
