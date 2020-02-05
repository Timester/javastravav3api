package javastrava.model.reference;

/**
 * @author danshannon
 * @param <T>
 *            The type of the enum's identifier (normally java.lang.String or java.lang.Integer)
 *
 */
public interface StravaReferenceType<T> {
	/**
	 * @return The description of the type instance
	 */
    String getDescription();

	/**
	 * @return The identifier of the type instance
	 */
    T getId();

	/**
	 * @return Return the value of the type instance
	 */
    T getValue();

	/**
	 * @see Object#toString()
	 */
	@Override
    String toString();

}
