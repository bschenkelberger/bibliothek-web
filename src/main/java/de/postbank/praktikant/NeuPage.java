package de.postbank.praktikant;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.postbank.praktikant.restpojo.Track;

public class NeuPage extends WebPage{

    public NeuPage() {
        super();
        setDefaultModel(new Model<Track>(new Track()));
        Form<Track> form = new Form<Track>("form");
        add(form);
        form.add(new TextField<String>("titel", new PropertyModel<String>(getDefaultModel(), "title")));
        form.add(new TextField<String>("singer", new PropertyModel<String>(getDefaultModel(), "singer")));

        form.add(new Button("speichern", new ResourceModel("speichernText")) {
            @Override
            public void onSubmit() {
                super.onSubmit();
                newDaten((Track) NeuPage.this.getDefaultModelObject());
                setResponsePage(new UebersichtPage(new PageParameters()));
            }

        });

        form.add(new Button("zurueck", new ResourceModel("zurueckText")) {
            @Override
            public void onSubmit() {
                super.onSubmit();
                setResponsePage(new UebersichtPage(new PageParameters()));
            }
        }.setDefaultFormProcessing(false));
    }
    
    private void newDaten(Track defaultModelObject) {
        // TODO Auto-generated method stub
        
    }

}
