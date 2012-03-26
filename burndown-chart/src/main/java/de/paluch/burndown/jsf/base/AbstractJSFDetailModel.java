package de.paluch.burndown.jsf.base;

/**
 *
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 21.03.2012
 *<br>
 *<br>
 * @param <T>
 */
public abstract class AbstractJSFDetailModel<T> extends AbstractJSFModel
{

	/**
	 *
	 */
	private static final long serialVersionUID = -8913428568732840367L;
	private T m_detail;

	public T getDetail()
	{

		return m_detail;
	}

	public void setDetail(T detail)
	{

		m_detail = detail;
	}

}
