package erp.workorder.dto;

import java.util.List;

public class WorkorderFilesResponse {

	private DocumentTypeDTO fileType;

	private List<WorkorderFileDTO> files;

	public WorkorderFilesResponse() {
		super();
	}

	public WorkorderFilesResponse(DocumentTypeDTO fileType, List<WorkorderFileDTO> files) {
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

	public List<WorkorderFileDTO> getFiles() {
		return files;
	}

	public void setFiles(List<WorkorderFileDTO> files) {
		this.files = files;
	}

}
