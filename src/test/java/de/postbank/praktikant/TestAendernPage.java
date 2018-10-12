package de.postbank.praktikant;

import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import de.postbank.praktikant.restpojo.Track;

public class TestAendernPage {
    
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new WicketApplication());
    }

    @Test
    public void homepageRendersSuccessfully() {
        // start and render the test page
        tester.startPage(new AendernPage(new Model<Track>(new Track())));

        // assert rendered page class
        tester.assertRenderedPage(AendernPage.class);
    }
}
