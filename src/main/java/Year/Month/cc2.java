package Year.Month;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
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

            // Save cookies to a file
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
            // Define the full path to save cookies.json locally at the specified directory
            String filePath = "C:\\Users\\suhsh\\eclipse-workspace\\Month2\\cookies.json";
            FileWriter file = new FileWriter(filePath);

            // Convert cookies to JSON-like format
            StringBuilder cookiesJson = new StringBuilder("[");

            int count = 0;
            for (Cookie cookie : cookies) {
                cookiesJson.append("{");
                cookiesJson.append("\"name\": \"").append(cookie.getName()).append("\", ");
                cookiesJson.append("\"value\": \"").append(cookie.getValue()).append("\", ");
                cookiesJson.append("\"domain\": \"").append(cookie.getDomain()).append("\", ");
                cookiesJson.append("\"path\": \"").append(cookie.getPath()).append("\", ");
                cookiesJson.append("\"secure\": ").append(cookie.isSecure()).append(", ");
                cookiesJson.append("\"httpOnly\": ").append(cookie.isHttpOnly());
                if (cookie.getExpiry() != null) {
                    cookiesJson.append(", \"expiry\": \"").append(cookie.getExpiry().toString()).append("\"");
                }
                cookiesJson.append("}");
                if (++count < cookies.size()) {
                    cookiesJson.append(", ");
                }
            }

            cookiesJson.append("]");

            // Write cookies to the file at the specified location
            file.write(cookiesJson.toString());
            file.close();

            System.out.println("Cookies have been saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void commitAndPushToGit() {
        try {
            // Path to the Git repository
            String repoDirectory = "C:\\Users\\suhsh\\eclipse-workspace\\Month2";

            // Git operations to add, commit, and push the changes
            new ProcessBuilder("cmd", "/c", "cd \"" + repoDirectory + "\" && git add cookies.json").inheritIO().start().waitFor();
            new ProcessBuilder("cmd", "/c", "cd \"" + repoDirectory + "\" && git commit -m \"Updated cookies.json with new cookie value\"").inheritIO().start().waitFor();
            new ProcessBuilder("cmd", "/c", "cd \"" + repoDirectory + "\" && git push origin main").inheritIO().start().waitFor();

            System.out.println("Changes have been committed and pushed to the Git repository.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
