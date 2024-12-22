import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.time.Duration;



public class ProjectTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void addTaskTest() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        try {
            driver.get("http://localhost:4200/project/1/board");
            Thread.sleep(2000);
            WebElement addTaskButton = driver.findElement(By.cssSelector(".add-task-btn"));
            addTaskButton.click();
            Thread.sleep(1000);
            WebElement titleInput = driver.findElement(By.id("basic-default-fullname"));
            titleInput.sendKeys("Test Task Title");
            Select prioritySelect = new Select(driver.findElement(By.id("prioritySelect")));
            prioritySelect.selectByValue("normal");
            WebElement deadlineInput = driver.findElement(By.id("html5-date-input-end"));
            deadlineInput.sendKeys("2024-12-25T10:00");
            Select userSelect = new Select(driver.findElement(By.id("exampleFormControlSelect1")));
            userSelect.selectByValue("4");
            WebElement saveButton = driver.findElement(By.xpath("//button[@type='submit' and text()='Save']"));
            saveButton.click();
            Thread.sleep(2000);
            WebElement taskList = driver.findElement(By.cssSelector(".task-list"));
            String taskListContent = taskList.getText();
            assertTrue(taskListContent.contains("Test Task Title"), "The task was not added!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }


    @Test
    public void testAddTaskWithEmptyField() {
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("http://localhost:4200/project/1/board");
            Thread.sleep(2000);
            WebElement addTaskButton = driver.findElement(By.cssSelector(".add-task-btn"));
            addTaskButton.click();
            Thread.sleep(1000);
            WebElement priorityField = driver.findElement(By.id("prioritySelect"));
            priorityField.sendKeys("high");
            WebElement deadlineField = driver.findElement(By.id("html5-date-input-end"));
            deadlineField.sendKeys("2024-12-31T12:00");
            WebElement saveButton = driver.findElement(By.cssSelector(".btn.btn-primary"));
            saveButton.click();
            Thread.sleep(1000);
            WebElement titleErrorMessage = driver.findElement(By.xpath("//div[contains(text(), 'Title is required')]"));
            String errorText = titleErrorMessage.getText();
            assertTrue(errorText.contains("Title is required"), "Le message d'erreur attendu ne s'affiche pas !");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testNavigation() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("http://localhost:4200/");
            Thread.sleep(2000);
            WebElement dashboardLink = driver.findElement(By.xpath("//div[text()='Mobile App Development']"));
            dashboardLink.click();
            Thread.sleep(2000);
            WebElement taskBoardHeader = driver.findElement(By.xpath("//div[text()='Task Board']"));
            assertTrue(taskBoardHeader.isDisplayed(), "Task Board page did not load properly!");
            WebElement taskBoardLink = driver.findElement(By.xpath("//div[text()='Mobile App Development']"));
            taskBoardLink.click();
            Thread.sleep(2000);
            WebElement dashboardHeader = driver.findElement(By.xpath("//div[text()='Mobile App Development']"));
            assertTrue(dashboardHeader.isDisplayed(), "Dashboard page did not load properly!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testDragDropTask() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("http://localhost:4200/project/1/board");

            // Wait for the page to load
            Thread.sleep(2000);

            WebElement toDoSection = driver.findElement(By.xpath("//span[text()='To Do']"));
            WebElement taskToMove = driver.findElement(By.xpath("//div[@class='section'][.//span[text()='To Do']]//div[@class='story']"));

            WebElement inProgressSection = driver.findElement(By.xpath("//span[text()='In Progress']"));

            Actions actions = new Actions(driver);
            actions.clickAndHold(taskToMove)
                    .moveToElement(inProgressSection)
                    .release()
                    .build().perform();

            Thread.sleep(2000);

            WebElement movedTask = driver.findElement(By.xpath("//span[text()='In Progress']//div[@class='story']//h3[contains(text(), 'Task Title')]"));
            assertTrue(movedTask.isDisplayed(), "The task was not moved successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }



    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}