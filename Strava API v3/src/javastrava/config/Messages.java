package javastrava.config;

import java.util.ResourceBundle;

/**
 * Internationalisable messages
 * 
 * @author Dan Shannon
 *
 */
public class Messages {
	/**
	 * Bundle name
	 */
	private static final String BUNDLE_NAME = "javastrava-messages";

	/**
	 * The resource bundle itseld
	 */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	/**
	 * Get the value of a property in the resource bundle
	 * 
	 * @param key
	 *            Name of the property
	 * @return The value of the property
	 */
	public static String string(final String key) {
		return RESOURCE_BUNDLE.getString(key);

	}

	/**
	 * no args constructor
	 */
	private Messages() {
	}
}
