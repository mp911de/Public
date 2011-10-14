package safetyfacade;

/**
 * Interface for running unsafe Code without return value. <br>
 * <br>
 * Project: safety-facade <br>
 * Autor: mark <br>
 * Created: 14.10.2011 <br>
 * <br>
 */
public interface IUnsafeVoid
{

	/**
	 * Contains unsafe Code (Exceptions might be thrown).
	 * 
	 * @throws Exception
	 *             generic Exception catch for SafetyFacade
	 */
	void run() throws Exception;
}
