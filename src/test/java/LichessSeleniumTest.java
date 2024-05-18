import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class LichessSeleniumTest {
    private WebDriver driver;
    private MainPage mainPage;

    private static String username;
    private static String password;
    private static String url;
    private static String mainPageTitle;
    private static String playerToSearch;
    
    @BeforeClass
    public static void loadConfig() {
        Properties props = new Properties();
        try (InputStream is = LichessSeleniumTest.class.getResourceAsStream("config.xml")) {
            props.loadFromXML(is);

            url = props.getProperty("url");
            username = props.getProperty("username");
            password = props.getProperty("password");
            mainPageTitle = props.getProperty("mainPageTitle");
            playerToSearch = props.getProperty("playerToSearch");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup()  throws MalformedURLException  {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
        driver.get(url);
        mainPage = new MainPage(this.driver);
    }

    @Test
    public void checkPageTitle() {
        Assert.assertEquals(mainPageTitle, mainPage.getTitle());
    }

    @Test
    public void searchForPlayer_PlayersProfileOpens() {
        ProfilePage profilePage = mainPage.searchForPlayer(playerToSearch);

        Assert.assertEquals(playerToSearch, profilePage.getProfileUsername());
    }

    @Test
    public void login_EditBiography_Logout() {
        // Login
        LoginPage loginPage = mainPage.clickLogin();
        mainPage = (MainPage)loginPage.login(username, password, false);

        Assert.assertEquals(username, mainPage.getUsername());

        // Edit bio
        EditProfilePage editProfilePage = mainPage.editProfile();
        editProfilePage.editBiography();
        ProfilePage profilePage = editProfilePage.submit();

        Assert.assertTrue(profilePage.isSuccess());

        // Logout
        loginPage = mainPage.logout();

        Assert.assertTrue(loginPage.getTitle().toLowerCase().contains("sign in"));
    }

    @Test
    public void search_ThenGoToSignInPage_ThenGoBackToMainPage() {
        // Search
        ProfilePage profilePage = mainPage.searchForPlayer(username);
        Assert.assertEquals(username, profilePage.getProfileUsername());

        // Login
        LoginPage loginPage = profilePage.clickLogin();
        Assert.assertTrue(loginPage.getTitle().toLowerCase().contains("sign in"));

        // Go back 1
        PageBase previousPage = loginPage.goBack();
        Assert.assertTrue(previousPage instanceof ProfilePage);
        Assert.assertEquals(username, ((ProfilePage)previousPage).getProfileUsername());

        // Go back 2
        previousPage = previousPage.goBack();
        Assert.assertTrue(previousPage instanceof MainPage);
        Assert.assertEquals(mainPageTitle, previousPage.getTitle());
    }
    
    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
