package de.paluch.burndown.jsf.base;

import java.util.Iterator;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

/**
 * <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 20.03.2012 <br>
 * <br>
 */
public abstract class AbstractJSFController {

    /**
     * <p>
     * Return the {@link UIComponent} (if any) with the specified <code>id</code>, searching recursively starting at the
     * specified <code>base</code>, and examining the base component itself, followed by examining all the base
     * component's facets and children. Unlike findComponent method of {@link UIComponentBase}, which skips recursive
     * scan each time it finds a {@link NamingContainer}, this method examines all components, regardless of their
     * namespace (assuming IDs are unique).
     * 
     * @param base
     *            Base {@link UIComponent} from which to search
     * @param id
     *            Component identifier to be matched
     * @return UIComponent
     */
    public static final UIComponent findComponent(UIComponent base, String id) {

        // Is the "base" component itself the match we are looking for?
        if (id.equals(base.getId())) {
            return base;
        }

        // Search through our facets and children
        UIComponent kid = null;
        UIComponent result = null;
        Iterator<UIComponent> kids = base.getFacetsAndChildren();
        while (kids.hasNext()) {
            kid = kids.next();
            if (id.equals(kid.getId())) {
                result = kid;
                break;
            }
            result = AbstractJSFController.findComponent(kid, id);
            if (result != null) {
                break;
            }
        }
        return result;
    }

    /**
     * @param id
     * @return UIComponent
     */
    public static final UIComponent findComponentInRoot(String id) {

        UIComponent ret = null;

        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            UIComponent root = context.getViewRoot();
            ret = AbstractJSFController.findComponent(root, id);
        }

        return ret;
    }
}
