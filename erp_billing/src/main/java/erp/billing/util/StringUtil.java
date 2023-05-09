package erp.billing.util;

public class StringUtil {

	public static final Boolean compareAlphaNumericStringIgnoringCase(String str1, String str2) {

		if (str1 != null && str2 != null
				&& str1.replaceAll("[^A-Za-z0-9]", "").equalsIgnoreCase(str2.replaceAll("[^A-Za-z0-9]", ""))) {
			return true;
		}
		return false;
	}

}
