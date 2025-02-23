package org.entraides;

import org.apache.wicket.util.tester.WicketTester;
import org.entraides.web.ApplicationWicket;
import org.entraides.web.PageAccueil;
import org.junit.Before;
import org.junit.Test;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage
{
	private WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester(new ApplicationWicket());
	}

	@Test
	public void homepageRendersSuccessfully()
	{
		//start and render the test page
		tester.startPage(PageAccueil.class);

		//assert rendered page class
		tester.assertRenderedPage(PageAccueil.class);
	}
}
