package de.postbank.praktikant.wicket;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringResourceStream;

/**
 * LinkColumn ist eine IStyledColumn Implementierung, in der kein Text sondern ein Link angezeigt wird.<br/>
 * Der Link-Handler wird durch die abstrakte Methode {@link #onClick(org.apache.wicket.ajax.AjaxRequestTarget, org.apache.wicket.model.IModel)}
 * definiert.
 * Der Link-Text kann auf 3 Wegen angegeben werden.
 * <ol>
 * <li>durch Übergabe eines statischen Models in {@link LinkColumn#LinkColumn(org.apache.wicket.model.IModel, org.apache.wicket.model.IModel)}<br/>
 * In diesem Fall wird für alle Zeilen das gleiche Model verwendet (sehr Resourcenschonend)</li>
 * <li>durch Überschreiben von {@link #createLabelModel(org.apache.wicket.model.IModel)}<br/>
 * Es kann ein beliebiges <code>Model&lt;String&gt;</code> erzeugt werden, für jede Zeile wird die Methode aufgerufen! Wenn also immer das gleiche
 * Model verwendet werden kann, darf es nicht in dieser Methode erzeugt werden, dann wenn möglich lieber Variante 1. nutzen.</li>
 * <li>durch Übergabe einer PropertyExpression in {@link LinkColumn#LinkColumn(org.apache.wicket.model.IModel, String)}<br/>
 * In diesem Fall wird die entsprechende Property des rowModel für die jeweilige Zeile genutzt, für jede Zeile wird ein eigenes Model erzeugt</li>
 * </ol>
 * Die Reihenfolge der Abfrage der zu verwendenden Methode entspricht der oben angegebenen Reihenfolge, falls
 * also ein statisches Model übergeben wird, wird dieses auf jeden Fall verwendet.
 * Die PropertyExpression wird nur ausgewertet, falls {@link #createLabelModel(org.apache.wicket.model.IModel)} nicht
 * überschrieben wurde (und dort nicht <code>super.createLabelModel(IModel)</code> aufgerufen wird).
 * 
 * @author Baschir Jaghoori (mgm technology partners GmbH)
 * @author René Wächter (Postbank Systems AG)
 * @param <T> Typ der in der Tabelle angezeigten Daten.
 * @param <S> Typ der Property
 */
public abstract class LinkColumn<T, S> extends PropertyColumn<T, S> {

    private IModel<String> linkDisplayModel;

    /**
     * Erstelle eine neue Spalte, die den Linktext aus einem IModel ausliest.
     * Bei Nutzung dieses Konstruktors MUSS {@link #createLabelModel(org.apache.wicket.model.IModel)} implementiert werden,
     * um ein korrektes LabelModel zu erhalten!
     * 
     * @param displayModel IModel für die Spaltenüberschrift.
     */
    public LinkColumn(IModel<String> displayModel) {
        super(displayModel, null);
    }

    /**
     * Erstelle eine neue Spalte, die den Linktext aus einem IModel ausliest.
     * 
     * @param displayModel IModel für die Spaltenüberschrift.
     * @param linkDisplayModel IModel für den Link Text.
     */
    public LinkColumn(IModel<String> displayModel, IModel<String> linkDisplayModel) {
        super(displayModel, null);
        this.linkDisplayModel = linkDisplayModel;
    }

    /**
     * Erstelle eine neue Spalte, die den Linktext aus einer Property
     * Expression ausliest. Die Property Espression wird auf die
     * jeweilige Zeile angewendet, so dass ein Property des Zeilenobjekts
     * als Link Text verwendet werden kann.
     * 
     * @param displayModel IModel für die Spaltenüberschrift.
     * @param propertyExpression Property Expression.
     */
    public LinkColumn(IModel<String> displayModel, String propertyExpression) {
        super(displayModel, propertyExpression);
    }

    @Override
    public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
        IModel<String> displayModel = (linkDisplayModel != null) ? linkDisplayModel : new PropertyModel<String>(rowModel, getPropertyExpression());
        cellItem.add(new LinkPanel<T>(this, componentId, rowModel, displayModel));
    }

    /**
     * Click Handler. Diese Methode muss implementiert werden, um den angeclickten Link zu verarbeiten.
     * 
     * @param target Ajax Target
     * @param rowModel Die angeclickte Zeile
     */
    protected abstract void onClick(AjaxRequestTarget target, IModel<T> rowModel);

    /**
     * Da {@link org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn} keine {@link Component} ist und
     * kein Template besitzt, wird das Markup des Links in dieser Methode erzeugt.<br/>
     * Diese Methode kann überschrieben werden, wenn das Default-Markup nicht ausreicht.
     * 
     * @param container the MarkupContainer which requests to load the Markup resource stream
     * @param containerClass the container the markup should be associated with
     * @return a IResourceStream if the resource was found
     */
    public IResourceStream getLinkMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
        return new StringResourceStream("<wicket:panel><a wicket:id=\"link\"><span wicket:id=\"label\"></span></a></wicket:panel>");
    }

    /**
     * Hilfspanel welches einen Link beinhaltet.
     * 
     * @param <V> Typ des Panels.
     */
    private final class LinkPanel<V> extends Panel implements IMarkupResourceStreamProvider {

        private LinkPanel(final LinkColumn<V, S> col, String componentId, IModel<V> rowModel, IModel<String> linkDisplayModel) {
            super(componentId);
            Link<V> link = new AjaxFallbackLink<V>("link", rowModel) {
                @Override
                public void onClick(AjaxRequestTarget target) {
                    col.onClick(target, getModel());
                }
            };
            link.add(new Label("label", linkDisplayModel));
            this.add(link);
        }

        /**
         * Da {@link org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn} keine {@link Component} ist
         * und kein Template besitzt, wird das Markup des Links in dieser Methode erzeugt.
         * 
         * @param container Container.
         * @param containerClass Klasse des Containers.
         * @return Stream.
         */
        public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
            return LinkColumn.this.getLinkMarkupResourceStream(container, containerClass);
        }
    }

    /**
     * @return CSS-Class, welche direkt am Link gesetzt werden sollen
     */
    public String getLinkCssClass() {
        return "";
    }

    /**
     * @return title-attribute, welche direkt am Link gesetzt werden sollen
     */
    public String getLinkTitleString() {
        return "";
    }

    @Override
    public void detach() {
        super.detach();
        if (null != linkDisplayModel) {
            linkDisplayModel.detach();
        }
    }

    private final class TitleModel extends AbstractReadOnlyModel<String> {
        @Override
        public String getObject() {
            return LinkColumn.this.getLinkTitleString();
        }
    }

    private final class CssClassModel extends AbstractReadOnlyModel<String> {
        @Override
        public String getObject() {
            return LinkColumn.this.getLinkCssClass();
        }
    }
}
