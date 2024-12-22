package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        try {
            driver.get("http://localhost/exercice2/connexion.html");


        } finally {
            driver.quit();
        }

    }

}