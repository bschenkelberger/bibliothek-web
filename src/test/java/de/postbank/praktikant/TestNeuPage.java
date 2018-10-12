package de.postbank.praktikant;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

public class TestNeuPage {

    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new WicketApplication());
    }

    @Test
    public void homepageRendersSuccessfully() {
        // start and render the test page
        tester.startPage(new NeuPage());

        // assert rendered page class
        tester.assertRenderedPage(NeuPage.class);
    }
}
