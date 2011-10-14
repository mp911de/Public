package safetyfacade;

/**
 * Interface for running unsafe Code and returning a value.
 * 
 * @param <T>
 *            Return-Type. <br>
 * <br>
 *            Project: safety-facade <br>
 *            Autor: mark <br>
 *            Created: 14.10.2011 <br>
 * <br>
 */
public interface IUnsafe<T>
{

	/**
	 * Contains unsafe Code (Exceptions might be thrown).
	 * 
	 * @return T Method result
	 * @throws Exception
	 *             generic Exception catch for SafetyFacade
	 */
	T run() throws Exception;
}
