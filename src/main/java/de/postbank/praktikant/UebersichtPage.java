package de.postbank.praktikant;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IStyledColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.postbank.praktikant.model.BooksModel;
import de.postbank.praktikant.response.BooksResponse;
import de.postbank.praktikant.restpojo.BookEntity;
import de.postbank.praktikant.restpojo.Track;
import de.postbank.praktikant.wicket.GenericTabelleDataProvider;
import de.postbank.praktikant.wicket.LinkColumn;

public class UebersichtPage extends WebPage {

	public UebersichtPage(final PageParameters parameters) {
		super(parameters);
		setDefaultModel(new BooksModel());

		IModel<List<BookEntity>> recContListModel = new ListModel<BookEntity>() {
			@Override
			public List<BookEntity> getObject() {
				return ((BooksResponse) UebersichtPage.this.getDefaultModelObject()).getBooks();
			}

		};
		ISortableDataProvider<BookEntity, String> dataProvider = new GenericTabelleDataProvider(recContListModel, "name", true);

		final DataTable<BookEntity, String> table = new DataTable<BookEntity, String>("repeater", getColumns(), dataProvider, Integer.MAX_VALUE);
		table.addTopToolbar(new HeadersToolbar(table, null));
		add(table);
		add(new Link("hinzufuegen", new ResourceModel("hinzufuegenButtonLabel")) {

			@Override
			public void onClick() {
				getPage().setResponsePage(new NeuPage());
			}

		});
	}

	private List<IStyledColumn<BookEntity, String>> getColumns() {
		List<IStyledColumn<BookEntity, String>> columnList = new ArrayList<IStyledColumn<BookEntity, String>>();
		columnList.add(new PropertyColumn<BookEntity, String>(new ResourceModel("header_1"), "name"));
		columnList.add(new PropertyColumn<BookEntity, String>(new ResourceModel("header_2"), "genre.name"));
		columnList.add(new AendernLinkColumn(new ResourceModel("aenderLinkLabel")));
		columnList.add(new LoeschenLinkColumn(new ResourceModel("loeschenLinkLabel")));
		return columnList;
	}

	private class AendernLinkColumn extends LinkColumn<BookEntity, String> {

		public AendernLinkColumn(IModel<String> displayModel) {
			super(displayModel, displayModel);
		}

		@Override
		protected void onClick(AjaxRequestTarget target, IModel<BookEntity> rowModel) {
			setResponsePage(new AendernPage(rowModel));
		}
	}

	private class LoeschenLinkColumn extends LinkColumn<BookEntity, String> {

		public LoeschenLinkColumn(IModel<String> displayModel) {
			super(displayModel, displayModel);
		}

		@Override
		protected void onClick(AjaxRequestTarget target, IModel<BookEntity> rowModel) {
			//setResponsePage(new AendernPage(rowModel));
		}
	}
}
