package de.postbank.praktikant.wicket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;


public class GenericTabelleDataProvider<T extends Serializable> extends SortableDataProvider<T, String> {

    private IModel<List<T>> model;
    
    private String searchKey;
    
    private String[] searchAttributes;
    
    public GenericTabelleDataProvider(IModel<List<T>> model, String sortProperty, boolean ascending) {
        this.model=model;
        setSort(new SortParam<String>(sortProperty, ascending));
    }

    @Override
    public Iterator<T> iterator(long first, long count) {
        List<T> copy = filter(model.getObject());
        if (getSort()!=null) {
            sort(copy, getSort().getProperty(), getSort().isAscending());
        }
        return copy.subList((int) first, (int) first + (int) count).iterator();
    }
    
    public void setSearchAttributes(String[] searchAttributes) {
        this.searchAttributes=searchAttributes.clone();
    }
    
    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public IModel<T> model(T object) {
        return new Model<T>(object);
    }

    public long size() {
        List<T> copy = filter(model.getObject());
        return copy.size();
    }
    
    private List<T> filter(List<T> liste) {
        ArrayList<T> retVal=null;
        if (searchAttributes==null || searchAttributes.length==0 || searchKey==null || searchKey.length()==0) {
            retVal=new ArrayList<T>(liste);
        } else {
            retVal=new ArrayList<T>();
            Iterator<T> iterator=liste.iterator();
            while (iterator.hasNext()) {
                T row=iterator.next();
                if (contains(row, searchAttributes)) {
                    retVal.add(row);
                }
            }
        }
        return retVal;
    }
    
    private boolean contains(T row, String[] searchAttributes) {
        boolean retVal=false;
        if (row!=null) {
            for (String attribute : searchAttributes) {
                if (contains(attribute, row)) {
                    retVal=true;
                    break;
                }
            }
        }
        return retVal;
    }
    
    protected boolean contains(String attribute, T row) {
        final Object value = PropertyResolver.getValue(attribute, row);
        if (value instanceof String) {
            return contains((String) value);
        }
        return false;
    }
    
    protected boolean contains(String value) {
        return value.toLowerCase().contains(searchKey.toLowerCase());
    }
    
    private void sort(
            List<T> liste,
            final String property, final boolean ascending) {        
        Collections.sort(liste, new MyComparator(property, ascending));        
    }
    
     /**
     * Comparator für die Sortierung der Tabelle.
     */
    private class MyComparator implements Comparator<T>, Serializable {
        private static final long serialVersionUID = 1L;
        private String property;
        private boolean ascending;

        public MyComparator(String property, boolean ascending) {
            this.property = property;
            this.ascending = ascending;
        }

        @SuppressWarnings("unchecked")
        public int compare(T o1, T o2) {
            int i;

            Comparable<Object> v1 = (Comparable<Object>) PropertyResolver.getValue(property, o1);
            Comparable<Object> v2 = (Comparable<Object>) PropertyResolver.getValue(property, o2);
            if (v1 == null) {
                i = -1;
            } else if (v2 == null) {
                i = 1;
            } else {
                i = v1.compareTo(v2);
            }
            if (!this.ascending) {
                i = -i;
            }
            return i;
        }
    }

    public void setModel(IModel<List<T>> model) {
        this.model=model;
    }
}
