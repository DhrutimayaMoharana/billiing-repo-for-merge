package erp.billing.feignClient.dto;

import java.util.List;

public class IrnReferenceDetailRequest {

	private String invRm;

	private IrnDocumentPeriodDetailRequest docPerdDtls;

	private List<IrnPrecedingDocumentDetailRequest> precDocDtls = null;

	private List<IrnContractDetailRequest> contrDtls = null;

	public String getInvRm() {
		return invRm;
	}

	public void setInvRm(String invRm) {
		this.invRm = invRm;
	}

	public IrnDocumentPeriodDetailRequest getDocPerdDtls() {
		return docPerdDtls;
	}

	public void setDocPerdDtls(IrnDocumentPeriodDetailRequest docPerdDtls) {
		this.docPerdDtls = docPerdDtls;
	}

	public List<IrnPrecedingDocumentDetailRequest> getPrecDocDtls() {
		return precDocDtls;
	}

	public void setPrecDocDtls(List<IrnPrecedingDocumentDetailRequest> precDocDtls) {
		this.precDocDtls = precDocDtls;
	}

	public List<IrnContractDetailRequest> getContrDtls() {
		return contrDtls;
	}

	public void setContrDtls(List<IrnContractDetailRequest> contrDtls) {
		this.contrDtls = contrDtls;
	}

}