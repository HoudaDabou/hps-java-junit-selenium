package com.coffeemachine;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.net.URL;

public class Actionwords {
    public WebDriver driver;
    public boolean handleWater = false;
    public boolean handleBeans = false;
    public boolean handleGrounds = false;

    public void createBrowser() {
        DesiredCapabilities caps = DesiredCapabilities.firefox();
        //caps.setCapability("platform", "Windows XP");
        //caps.setCapability("version", "51.0");
        //caps.setCapability("marionette", "false");

        try {
            driver = new FirefoxDriver(caps); //RemoteWebDriver(new URL(URL), caps);
        } catch (Exception err) {
            System.out.println("============-------------------------------------------------------------");
            System.out.println(err);
            System.out.println("============-------------------------------------------------------------");

        }
    }
    public void shutdownBrowser() {
        driver.quit();
    }

    public void iStartTheCoffeeMachineUsingLanguageLang(String lang) {
        driver.get("https://hiptest.github.io/hps-java-junit-selenium/src/web/coffee_machine.html");

        new Select(driver.findElement(By.id("lang"))).selectByVisibleText(lang);
        driver.findElement(By.id("onOff")).click();
    }

    public void iShutdownTheCoffeeMachine() {
        driver.findElement(By.id("onOff")).click();
    }

    public void messageMessageShouldBeDisplayed(String message) {
        assertEquals(driver.findElement(By.id("message")).getText(), message);
    }

    public void coffeeShouldBeServed() {
        assertTrue(driver.findElement(By.id("coffee")).getAttribute("class").contains("served"));
    }

    public void coffeeShouldNotBeServed() {
        assertFalse(driver.findElement(By.id("coffee")).getAttribute("class").contains("served"));
    }

    public void iTakeACoffee() {
        driver.findElement(By.id("getCoffee")).click();

        if (handleWater) {
            iFillTheWaterTank();
        }

        if (handleBeans) {
            iFillTheBeansTank();
        }

        if (handleGrounds) {
            iEmptyTheCoffeeGrounds();
        }
    }

    public void iEmptyTheCoffeeGrounds() {
        driver.findElement(By.id("emptyGround")).click();
    }

    public void iFillTheBeansTank() {
        driver.findElement(By.id("fillBeans")).click();
    }

    public void iFillTheWaterTank() {
        driver.findElement(By.id("fillWater")).click();
    }

    public void iTakeCoffeeNumberCoffees(int coffeeNumber) {
        while ((coffeeNumber > 0)) {
            iTakeACoffee();
            coffeeNumber = coffeeNumber - 1;
        }
    }

    public void theCoffeeMachineIsStarted() {
        iStartTheCoffeeMachineUsingLanguageLang("en");
    }

    public void iHandleEverythingExceptTheWaterTank() {
        iHandleCoffeeGrounds();
        iHandleBeans();
    }

    public void iHandleWaterTank() {
        handleWater = true;
    }

    public void iHandleBeans() {
        handleBeans = true;
    }

    public void iHandleCoffeeGrounds() {
        handleGrounds = true;
    }

    public void iHandleEverythingExceptTheBeans() {
        iHandleWaterTank();
        iHandleCoffeeGrounds();
    }

    public void iHandleEverythingExceptTheGrounds() {
        iHandleWaterTank();
        iHandleBeans();
    }

    public void displayedMessageIs(String freeText) {
        messageMessageShouldBeDisplayed(freeText);
    }

    public void iSwitchToSettingsMode() {
        driver.findElement(By.id("settings")).click();
    }

    public void settingsShouldBe(String datatable) {
        String settings = "";
        for (String line : datatable.split("\n")) {
            String[] cells = line.split("\\|");
            settings = settings + cells[1].trim() + ": " + cells[2].trim() + "\n";
        }

        assertEquals(driver.findElement(By.id("settingsDisplay")).getText(), settings);
    }
}
