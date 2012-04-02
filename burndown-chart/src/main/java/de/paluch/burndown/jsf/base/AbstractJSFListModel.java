package de.paluch.burndown.jsf.base;

import java.util.ArrayList;
import java.util.List;

/**
 * <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 21.03.2012 <br>
 * <br>
 * 
 * @param <T>
 */
public abstract class AbstractJSFListModel<T> extends AbstractJSFModel {

    /**
	 *
	 */
    private static final long serialVersionUID = -2044067718130918418L;
    private int currentPage = 0;
    private List<T> list = new ArrayList<T>();
    private String tableId = "";

    public int getCurrentPage() {

        return currentPage;
    }

    public List<T> getList() {

        return list;
    }

    public String getTableId() {

        return tableId;
    }

    public abstract void refreshList();

    public void setCurrentPage(int currentPage) {

        this.currentPage = currentPage;
    }

    public void setTableId(String tableId) {

        this.tableId = tableId;
    }

}
