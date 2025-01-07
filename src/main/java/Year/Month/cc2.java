package Year.Month;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class cc2 
{
    public static void main(String[] args) 
    {
        // Set the path for the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\suhsh\\chromedriver-win64\\chromedriver.exe");
        
        // Initialize WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to the website
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
            String cookiesFilePath = "C:\\Users\\suhsh\\eclipse-workspace\\Project1\\src\\cookies.json";
            saveCookiesToFile(cookies, cookiesFilePath);

            System.out.println("Cookies have been saved to cookies.json");

            // Commit and push changes to Git
            commitAndPushToGit(cookiesFilePath);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }

    private static void saveCookiesToFile(Set<Cookie> cookies, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write("[");
            int count = 0;
            for (Cookie cookie : cookies) {
                fileWriter.write("{");
                fileWriter.write("\"name\": \"" + cookie.getName() + "\", ");
                fileWriter.write("\"value\": \"" + cookie.getValue() + "\", ");
                fileWriter.write("\"domain\": \"" + cookie.getDomain() + "\", ");
                fileWriter.write("\"path\": \"" + cookie.getPath() + "\", ");
                fileWriter.write("\"secure\": " + cookie.isSecure() + ", ");
                fileWriter.write("\"httpOnly\": " + cookie.isHttpOnly());
                if (cookie.getExpiry() != null) {
                    fileWriter.write(", \"expiry\": \"" + cookie.getExpiry().toString() + "\"");
                }
                fileWriter.write("}");
                if (++count < cookies.size()) {
                    fileWriter.write(", ");
                }
            }
            fileWriter.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void commitAndPushToGit(String filePath) {
        try {
            String repoDirectory = "C:\\Users\\suhsh\\eclipse-workspace\\Project1\\src\\";

            // Configure safe directory
            new ProcessBuilder(
                "cmd", "/c", "git config --global --add safe.directory \"" + repoDirectory + "\""
            ).inheritIO().start().waitFor();

            // Add changes to Git
            new ProcessBuilder(
                "cmd", "/c", "cd \"" + repoDirectory + "\" && git add \"" + filePath + "\""
            ).inheritIO().start().waitFor();

            // Commit changes
            new ProcessBuilder(
                "cmd", "/c", "cd \"" + repoDirectory + "\" && git commit -m \"Updated cookies.json with new cookie value\""
            ).inheritIO().start().waitFor();

            // Push changes to the repository
            new ProcessBuilder(
                "cmd", "/c", "cd \"" + repoDirectory + "\" && git push origin main"
            ).inheritIO().start().waitFor();

            System.out.println("Changes have been committed and pushed to the Git repository.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
