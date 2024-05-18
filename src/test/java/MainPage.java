import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class MainPage extends PageBase {
    public MainPage(WebDriver driver, PageBase prevPage) {
        super(driver, prevPage);
    }
    
    public MainPage(WebDriver driver) {
        this(driver, null);
    }
}
