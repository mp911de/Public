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
abstract class AbstractModelBuilder<T extends AbstractModel>
{

	public abstract T createNewModel();

	public abstract void refreshModel(T old);
}
