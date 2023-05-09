package erp.workorder.util;

import erp.workorder.dto.UserDetail;

public class LogUtil {

	public static final String beforeExecutionLogMessage(UserDetail user, String methodName) {

		return methodName + " execution initialized by user with code : " + user.getCode() + " & name : "
				+ user.getName() + " & companyId : " + user.getCompanyId();
	}

	public static final String afterExecutionLogMessage(UserDetail user, String methodName) {

		return methodName + " executed by user with code : " + user.getCode() + " & name : " + user.getName()
				+ " & companyId : " + user.getCompanyId();
	}

}
