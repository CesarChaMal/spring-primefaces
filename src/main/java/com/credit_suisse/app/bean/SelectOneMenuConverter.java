package com.credit_suisse.app.bean;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("selectOneMenuConverter")
public class SelectOneMenuConverter implements Converter {
 
    @Override
    public Object getAsObject(final FacesContext arg0, final UIComponent arg1, final String objectString) {
        if (objectString == null) {
            return null;
        }
 
        return fromSelect(arg1, objectString);
    }
 
    private String serialize(final Object object) {
        if (object == null) {
            return null;
        }
        return object.getClass() + "@" + object.hashCode();
    }
 
    private Object fromSelect(final UIComponent currentcomponent, final String objectString) {
 
        if (currentcomponent.getClass() == UISelectItem.class) {
            final UISelectItem item = (UISelectItem) currentcomponent;
            final Object value = item.getValue();
            if (objectString.equals(serialize(value))) {
                return value;
            }
        }
 
        if (currentcomponent.getClass() == UISelectItems.class) {
            final UISelectItems items = (UISelectItems) currentcomponent;
            final List<Object> elements = (List<Object>) items.getValue();
            for (final Object element : elements) {
                if (objectString.equals(serialize(element))) {
                    return element;
                }
            }
        }
 
 
        if (!currentcomponent.getChildren().isEmpty()) {
            for (final UIComponent component : currentcomponent.getChildren()) {
                final Object result = fromSelect(component, objectString);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
 
    @Override
    public String getAsString(final FacesContext arg0, final UIComponent arg1, final Object object) {
//        if(object != null) {
//            return String.valueOf(((Task) object).getId());
//        }
//        else {
//            return null;
//        }
        return serialize(object);
    }
 
}