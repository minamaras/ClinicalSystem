package com.example.ClinicalSystem.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MakeARequestForAppointment {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"requestbutton\"]")
    private WebElement requestbutton;

    public MakeARequestForAppointment(WebDriver driver) {
        this.driver = driver;
    }

    public MakeARequestForAppointment() {
    }

    public WebDriver getDriver() {
        return driver;
    }


    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getRequestbutton() {
        return requestbutton;
    }

    public void setRequestbutton(WebElement requestbutton) {
        this.requestbutton = requestbutton;
    }
}
