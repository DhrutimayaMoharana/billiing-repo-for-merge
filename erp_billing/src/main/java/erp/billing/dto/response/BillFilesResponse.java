package erp.billing.dto.response;

import java.util.List;

import erp.billing.dto.BillFileDTO;
import erp.billing.dto.DocumentTypeDTO;

public class BillFilesResponse {

	private DocumentTypeDTO fileType;

	private List<BillFileDTO> files;

	public BillFilesResponse() {
		super();
	}

	public BillFilesResponse(DocumentTypeDTO fileType, List<BillFileDTO> files) {
		super();
		this.fileType = fileType;
		this.files = files;
	}

	public DocumentTypeDTO getFileType() {
		return fileType;
	}

	public void setFileType(DocumentTypeDTO fileType) {
		this.fileType = fileType;
	}

	public List<BillFileDTO> getFiles() {
		return files;
	}

	public void setFiles(List<BillFileDTO> files) {
		this.files = files;
	}

}
