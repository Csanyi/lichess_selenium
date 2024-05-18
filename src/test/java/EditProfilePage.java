import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

public class EditProfilePage extends PageBase {
    private By bioLocator = By.name("bio");
    private By submitLocator = By.className("submit");

    public EditProfilePage(WebDriver driver, PageBase prevPage) {
        super(driver, prevPage);
    }

    public void editBiography(String content) {
        WebElement bioTextArea = this.waitAndReturnElement(bioLocator);
        bioTextArea.clear();
        bioTextArea.sendKeys(content);
    }

    public void editBiography() {
        WebElement bioTextArea = this.waitAndReturnElement(bioLocator);
        String bioText = bioTextArea.getText();

        int num = 0;
        try {
            num = Integer.parseInt(bioText);
            ++num;
        } catch (NumberFormatException e) { /* num remains 0 */ }

        bioTextArea.clear();
        bioTextArea.sendKeys(String.valueOf(num));
    }

    public ProfilePage submit() {
        this.waitAndReturnElement(submitLocator).click();

        return new ProfilePage(this.driver, this);
    }
}
