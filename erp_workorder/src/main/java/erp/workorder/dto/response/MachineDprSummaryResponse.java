package erp.workorder.dto.response;

import java.util.List;

public class MachineDprSummaryResponse {

	List<MachineDprGetResponse> dprList;

	private Double netTotalPrimaryMeterRead;

	private Double netTotalPrimaryActualRead;

	private Double netTotalSecondaryMeterRead;

	private Double netTotalSecondaryActualRead;

	public MachineDprSummaryResponse() {
		super();
	}

	public MachineDprSummaryResponse(List<MachineDprGetResponse> dprList, Double netTotalPrimaryMeterRead,
			Double netTotalPrimaryActualRead, Double netTotalSecondaryMeterRead, Double netTotalSecondaryActualRead) {
		super();
		this.dprList = dprList;
		this.netTotalPrimaryMeterRead = netTotalPrimaryMeterRead;
		this.netTotalPrimaryActualRead = netTotalPrimaryActualRead;
		this.netTotalSecondaryMeterRead = netTotalSecondaryMeterRead;
		this.netTotalSecondaryActualRead = netTotalSecondaryActualRead;
	}

	public List<MachineDprGetResponse> getDprList() {
		return dprList;
	}

	public void setDprList(List<MachineDprGetResponse> dprList) {
		this.dprList = dprList;
	}

	public Double getNetTotalPrimaryMeterRead() {
		return netTotalPrimaryMeterRead;
	}

	public void setNetTotalPrimaryMeterRead(Double netTotalPrimaryMeterRead) {
		this.netTotalPrimaryMeterRead = netTotalPrimaryMeterRead;
	}

	public Double getNetTotalPrimaryActualRead() {
		return netTotalPrimaryActualRead;
	}

	public void setNetTotalPrimaryActualRead(Double netTotalPrimaryActualRead) {
		this.netTotalPrimaryActualRead = netTotalPrimaryActualRead;
	}

	public Double getNetTotalSecondaryMeterRead() {
		return netTotalSecondaryMeterRead;
	}

	public void setNetTotalSecondaryMeterRead(Double netTotalSecondaryMeterRead) {
		this.netTotalSecondaryMeterRead = netTotalSecondaryMeterRead;
	}

	public Double getNetTotalSecondaryActualRead() {
		return netTotalSecondaryActualRead;
	}

	public void setNetTotalSecondaryActualRead(Double netTotalSecondaryActualRead) {
		this.netTotalSecondaryActualRead = netTotalSecondaryActualRead;
	}

}
