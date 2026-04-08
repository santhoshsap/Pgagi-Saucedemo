package org.sacuedemo.packag;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

	// Reusable login method with WAIT
	public void login(String username, String password) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).clear();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))).sendKeys(username);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys(password);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button"))).click();
	}

	@Test(priority = 1)
	public void validLogin() {
		login("standard_user", "secret_sauce");
		System.out.println("Login Successful");
	}

	@Test(priority = 2)
	public void invalidLogin() {
		driver.get("https://www.saucedemo.com/");

		login("wrong", "wrong");

		String error = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3"))).getText();

		System.out.println("Error Message: " + error);
	}

	@Test(priority = 3)
	public void productListValidation() {
		driver.get("https://www.saucedemo.com/");

		login("standard_user", "secret_sauce");

		int count = driver.findElements(By.className("inventory_item")).size();
		System.out.println("Products count: " + count);
	}

	@Test(priority = 4)
	public void addToCart() {
		driver.get("https://www.saucedemo.com/");

		login("standard_user", "secret_sauce");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-backpack"))).click();

		String cartCount = driver.findElement(By.className("shopping_cart_badge")).getText();
		System.out.println("Cart count: " + cartCount);
	}

	
	
	
	@Test(priority = 5)
	public void cartValidation() {

	    driver.get("https://www.saucedemo.com/");
	    login("standard_user", "secret_sauce");

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.id("add-to-cart-sauce-labs-backpack"))).click();

	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.className("shopping_cart_link"))).click();

	    String product = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.className("inventory_item_name"))).getText();

	    System.out.println("Product in cart: " + product);
	}
	

	@Test(priority = 6)
	public void checkoutFlow() {
		driver.get("https://www.saucedemo.com/");

		login("standard_user", "secret_sauce");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-backpack"))).click();
		driver.findElement(By.className("shopping_cart_link")).click();
		driver.findElement(By.id("checkout")).click();

		driver.findElement(By.id("first-name")).sendKeys("Abi");
		driver.findElement(By.id("last-name")).sendKeys("Test");
		driver.findElement(By.id("postal-code")).sendKeys("600001");

		driver.findElement(By.id("continue")).click();

		System.out.println("Checkout Step Completed");
	}

	@Test(priority = 7)
	public void logoutTest() {
		driver.get("https://www.saucedemo.com/");

		login("standard_user", "secret_sauce");

		driver.findElement(By.id("react-burger-menu-btn")).click();
		driver.findElement(By.id("logout_sidebar_link")).click();

		System.out.println("Logout successful");
	}
}