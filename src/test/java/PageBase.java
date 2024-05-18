import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.By;

public abstract class PageBase {
    protected WebDriver driver;
    protected WebDriverWait wait;

    private By loginLocator = By.className("signin");
    private By userTagLocator = By.id("user_tag");
    private By preferencesLocator = By.linkText("Preferences");
    private By logoutLocator = By.className("logout");
    private By searchButtonLocator = By.xpath("//header//div[@id='clinput']");
    private By searchFieldLocator = By.xpath("//header//input[@placeholder='Search']");

    protected PageBase prevPage;
    
    protected PageBase(WebDriver driver, PageBase prevPage) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        this.prevPage = prevPage;
    }

    protected PageBase(WebDriver driver) {
        this(driver, null);
    }
    
    protected WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    public LoginPage clickLogin() {
        if (isLoggedIn()) {
            return null;
        }

        this.waitAndReturnElement(loginLocator).click();
        
        return new LoginPage(this.driver, this);
    }

    public EditProfilePage editProfile() {
        if (!isLoggedIn()) {
             return null;
        }

        this.waitAndReturnElement(userTagLocator).click();
        this.waitAndReturnElement(preferencesLocator).click();

        return new EditProfilePage(this.driver, this);
    }

    public LoginPage logout() {
        if (!isLoggedIn()) {
            return null;
        }

        this.waitAndReturnElement(userTagLocator).click();
        this.waitAndReturnElement(logoutLocator).click();

        return new LoginPage(this.driver, this);
    }

    public ProfilePage searchForPlayer(String username) {
        this.waitAndReturnElement(searchButtonLocator).click();
        this.waitAndReturnElement(searchFieldLocator).sendKeys(username + '\n');

        return new ProfilePage(this.driver, this);
    }

    public boolean isLoggedIn() {
        return !this.driver.findElements(userTagLocator).isEmpty();
    }

    public String getUsername() {
        return this.waitAndReturnElement(userTagLocator).getText();
    }

    public String getTitle() {
        return this.driver.getTitle();
    }

    public PageBase goBack() {
        this.driver.navigate().back();

        return prevPage;
    }
}
