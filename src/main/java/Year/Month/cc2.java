package Year.Month;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.nio.file.Paths;
import java.nio.file.Path;
import io.github.bonigarcia.wdm.WebDriverManager;


public class cc2 {
    public static void main(String[] args) {
        // Use WebDriverManager to set up ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Your existing logic for login, cookie fetching, etc.
            driver.get("https://kommune.mainmanager.is/mmv2/MMV2Login.aspx");

            // Perform login
            driver.findElement(By.id("lgnUserLogin_UserName")).sendKeys("NAV");
            driver.findElement(By.id("lgnUserLogin_Password")).sendKeys("Testing@!123");
            driver.findElement(By.id("lgnUserLogin_Login")).click();

            // Wait for the page to load
            Thread.sleep(5000);

            // Fetch cookies
            Set<Cookie> cookies = driver.manage().getCookies();

            // Print specific cookie value
            String cookieName = "ASP.NET_SessionId";
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    System.out.println("Exact Cookie (" + cookieName + "): " + cookie.getValue());
                    break;
                }
            }

            // Save cookies to a file (without using local paths)
            saveCookiesToFile(cookies);

            System.out.println("Cookies have been saved.");

            // Commit and push changes to Git
            commitAndPushToGit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }

    private static void saveCookiesToFile(Set<Cookie> cookies) {
        try {
            // Modify this to save the cookies without using a local file path
            // You could either push it directly to Git or handle it in-memory
            String cookiesJson = "[";

            int count = 0;
            for (Cookie cookie : cookies) {
                cookiesJson += "{";
                cookiesJson += "\"name\": \"" + cookie.getName() + "\", ";
                cookiesJson += "\"value\": \"" + cookie.getValue() + "\", ";
                cookiesJson += "\"domain\": \"" + cookie.getDomain() + "\", ";
                cookiesJson += "\"path\": \"" + cookie.getPath() + "\", ";
                cookiesJson += "\"secure\": " + cookie.isSecure() + ", ";
                cookiesJson += "\"httpOnly\": " + cookie.isHttpOnly();
                if (cookie.getExpiry() != null) {
                    cookiesJson += ", \"expiry\": \"" + cookie.getExpiry().toString() + "\"";
                }
                cookiesJson += "}";
                if (++count < cookies.size()) {
                    cookiesJson += ", ";
                }
            }

            cookiesJson += "]";

            // You can either print, send it over a network, or push it to Git directly
            System.out.println(cookiesJson);
            // Handle cookiesJson accordingly (e.g., push directly to Git)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void commitAndPushToGit() {
        try {
            // Skip the file path part here, and directly handle the commit logic
            String repoDirectory = "C:\\Users\\suhsh\\eclipse-workspace\\Month2";

            // Git operations (just like in your existing method)
            new ProcessBuilder("cmd", "/c", "cd \"" + repoDirectory + "\" && git add .").inheritIO().start().waitFor();
            new ProcessBuilder("cmd", "/c", "cd \"" + repoDirectory + "\" && git commit -m \"Updated cookies.json with new cookie value\"").inheritIO().start().waitFor();
            new ProcessBuilder("cmd", "/c", "cd \"" + repoDirectory + "\" && git push origin master").inheritIO().start().waitFor();

            System.out.println("Changes have been committed and pushed to the Git repository.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


