package erp.workorder.util;

import java.util.Map;

public class CustomSqlHelper {

	public static String sqlDelimiter = "%%";

	public static Double defaultDoubleVariableValue = 0.0;

	public static String defaultStringVariableValue = "--";

	public static String getUpdatedSqlExecutionString(String sqlQuery, Map<String, Object> sqlVariables) {

		if (sqlVariables != null) {
			sqlQuery = sqlQuery.toLowerCase();
			for (String mapKey : sqlVariables.keySet()) {
				sqlQuery = sqlQuery.replace(sqlDelimiter + mapKey.toLowerCase(), sqlVariables.get(mapKey).toString());
			}
			return sqlQuery;
		}
		return sqlQuery;
	}

}
