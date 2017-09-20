
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;

public class WaitForCondition {

	static long DEFAULT_TIMEOUT = 50;
	static long DEFAULT_POLL_SLEEP = 3000;

	Function<SearchContext, WebElement> elementLocated(final By by) {
		return new Function<SearchContext, WebElement>() {
			public WebElement apply(SearchContext context) {
				return context.findElement(by);
			}
		};
	}

	public WebElement findElement(SearchContext context, By by,
			long timeoutSeconds, long sleepMilliseconds) {
		FluentWait<SearchContext> wait = new FluentWait<SearchContext>(context)
				.withTimeout(timeoutSeconds, TimeUnit.SECONDS)
				.pollingEvery(sleepMilliseconds, TimeUnit.MILLISECONDS)
				.ignoring(NotFoundException.class);
		WebElement element = null;
		try {
			element = wait.until(elementLocated(by));
		} catch (Exception te) {
			element = null;
		}
		return element;
	}

	public WebElement findElement(SearchContext context, By by) {
		return findElement(context, by, DEFAULT_TIMEOUT, DEFAULT_POLL_SLEEP);
	}

}
