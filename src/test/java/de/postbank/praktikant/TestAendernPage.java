package de.postbank.praktikant;

import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import de.postbank.praktikant.restpojo.BookEntity;

public class TestAendernPage {
    
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new WicketApplication());
    }

    @Test
    public void homepageRendersSuccessfully() {
        // start and render the test page
        tester.startPage(new AendernPage(new Model<BookEntity>(new BookEntity())));

        // assert rendered page class
        tester.assertRenderedPage(AendernPage.class);
    }
}
