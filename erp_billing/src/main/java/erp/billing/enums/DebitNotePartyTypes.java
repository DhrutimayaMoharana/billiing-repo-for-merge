package erp.billing.enums;

public enum DebitNotePartyTypes {

	Employee(1), Contractor(2);

	private int partyType;

	DebitNotePartyTypes(Integer partyType) {
		this.partyType = partyType;
	}

	public int getPartyType() {
		return partyType;
	}

}
