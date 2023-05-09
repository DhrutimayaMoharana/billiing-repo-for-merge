package erp.billing.dao;

import erp.billing.entity.ClientIrnCredential;

public interface ClientIrnCredentialDao {

	ClientIrnCredential fetchClientByCompanyIdAndSiteId(Integer companyId, Integer siteId);

}
