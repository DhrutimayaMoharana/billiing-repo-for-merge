package erp.billing.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import erp.billing.dto.BillBoqItemDTO;
import erp.billing.dto.BillBoqQuantityItemDTO;
import erp.billing.dto.BillDTO;
import erp.billing.dto.BillDeductionDTO;
import erp.billing.dto.BillDeductionMappingDTO;
import erp.billing.dto.BillFileDTO;
import erp.billing.dto.BillTypeDTO;
import erp.billing.dto.BoqItemDTO;
import erp.billing.dto.CategoryItemDTO;
import erp.billing.dto.ChainageDTO;
import erp.billing.dto.ChainageTraverseDTO;
import erp.billing.dto.CompanyClientDTO;
import erp.billing.dto.CompanyDTO;
import erp.billing.dto.ContractorBankDetailDTO;
import erp.billing.dto.ContractorBillingAddressDTO;
import erp.billing.dto.ContractorDTO;
import erp.billing.dto.CountryStateDTO;
import erp.billing.dto.CurrencyDTO;
import erp.billing.dto.DebitNoteItemDTO;
import erp.billing.dto.DocumentTypeDTO;
import erp.billing.dto.EmployeeDTO;
import erp.billing.dto.EngineStateDTO;
import erp.billing.dto.FileDTO;
import erp.billing.dto.MaterialDTO;
import erp.billing.dto.S3FileDTO;
import erp.billing.dto.SiteDTO;
import erp.billing.dto.SubcategoryItemDTO;
import erp.billing.dto.UnitDTO;
import erp.billing.dto.UnitTypeDTO;
import erp.billing.dto.UserDetail;
import erp.billing.dto.WorkorderContractorDTO;
import erp.billing.dto.WorkorderDTO;
import erp.billing.dto.WorkorderTypeDTO;
import erp.billing.dto.request.ClientInvoiceAddUpdateRequest;
import erp.billing.dto.request.ClientInvoiceProductAddRequest;
import erp.billing.dto.request.WorkorderLabourAddUpdateRequest;
import erp.billing.dto.request.WorkorderLabourDepartmentAddUpdateRequest;
import erp.billing.dto.request.WorkorderLabourDesignationAddUpdateRequest;
import erp.billing.dto.request.WorkorderLabourTypeAddUpdateRequest;
import erp.billing.dto.response.BillDeductionResponseDto;
import erp.billing.dto.response.ClientInvoiceFetchResponse;
import erp.billing.dto.response.ClientInvoiceIrnInfoResponse;
import erp.billing.dto.response.ClientInvoiceStateTransitionFetchResponse;
import erp.billing.dto.response.WorkorderLabourDepartmentFetchResponse;
import erp.billing.dto.response.WorkorderLabourDesignationFetchResponse;
import erp.billing.dto.response.WorkorderLabourFetchResponse;
import erp.billing.dto.response.WorkorderLabourTypeFetchResponse;
import erp.billing.entity.Bill;
import erp.billing.entity.BillBoqItem;
import erp.billing.entity.BillBoqQuantityItem;
import erp.billing.entity.BillBoqQuantityItemTransac;
import erp.billing.entity.BillDeduction;
import erp.billing.entity.BillDeductionMapTransac;
import erp.billing.entity.BillDeductionMapping;
import erp.billing.entity.BillDeductionMappingV2;
import erp.billing.entity.BillFile;
import erp.billing.entity.BillTransac;
import erp.billing.entity.BillType;
import erp.billing.entity.BoqItem;
import erp.billing.entity.CategoryItem;
import erp.billing.entity.Chainage;
import erp.billing.entity.ChainageTraverse;
import erp.billing.entity.City;
import erp.billing.entity.ClientImplementationUnit;
import erp.billing.entity.ClientInvoice;
import erp.billing.entity.ClientInvoiceIrnInfo;
import erp.billing.entity.ClientInvoiceProduct;
import erp.billing.entity.ClientInvoiceStateTransitionMappingV2;
import erp.billing.entity.Company;
import erp.billing.entity.CompanyClient;
import erp.billing.entity.Contractor;
import erp.billing.entity.ContractorBankDetail;
import erp.billing.entity.ContractorBillingAddress;
import erp.billing.entity.CountryState;
import erp.billing.entity.Currency;
import erp.billing.entity.DebitNoteItem;
import erp.billing.entity.DocumentType;
import erp.billing.entity.Employee;
import erp.billing.entity.EngineState;
import erp.billing.entity.FileEntity;
import erp.billing.entity.GstManagement;
import erp.billing.entity.Material;
import erp.billing.entity.S3File;
import erp.billing.entity.Site;
import erp.billing.entity.SubcategoryItem;
import erp.billing.entity.TransportMode;
import erp.billing.entity.Unit;
import erp.billing.entity.UnitType;
import erp.billing.entity.Workorder;
import erp.billing.entity.WorkorderContractor;
import erp.billing.entity.WorkorderLabour;
import erp.billing.entity.WorkorderLabourDepartment;
import erp.billing.entity.WorkorderLabourDesignation;
import erp.billing.entity.WorkorderLabourType;
import erp.billing.entity.WorkorderType;

@Component
public class SetObject {

//	@Autowired
//	private UtilityService utilityService;

	public static final String fileSavingPath = "/opt/erp/";

	public static final String folderName = "erp_files/";

	public static final Integer billingModuleId = 2;

	public static final Double temporaryFoValue = 90.00;

	public static final Integer storeDieselDepartmentId = 4;

	public static final String temporary = "TO_BE_DELETED";

	public static String getCharForNumber(int i) {
		return i > 0 && i < 27 ? String.valueOf((char) (i + 'A' - 1)) : null;
	}

	public static final UserDetail getUserDetailFromHttpRequest(HttpServletRequest request) {

		String userId = request.getHeader("userId");
		String userCode = request.getHeader("userCode");
		String userCompanyId = request.getHeader("userCompanyId");
		String userRoleId = request.getHeader("userRoleId");
		String userName = request.getHeader("userName");
		if (userId == null || userId.isEmpty())
			return new UserDetail();
		return new UserDetail(Long.parseLong(userId), userCode, userName, Integer.parseInt(userRoleId),
				Integer.parseInt(userCompanyId));
	}

	public MaterialDTO materialEntityToDto(Material obj) {
		if (obj == null)
			return null;
		return new MaterialDTO(obj.getId(), obj.getName(), obj.getSpecification(), obj.getIsActive(),
				obj.getCreatedOn(), unitEntityToDto(obj.getUnit()), obj.getCompanyId());
	}

	public DebitNoteItemDTO debitNoteItemEntityToDto(DebitNoteItem obj) {
		if (obj == null)
			return null;
		return new DebitNoteItemDTO(obj.getId(), obj.getWorkorderId(), materialEntityToDto(obj.getMaterial()),
				obj.getDepartmentId(), obj.getFromDate(), obj.getToDate(), obj.getQuantity(), obj.getRate(),
				obj.getAmount(), obj.getHandlingCharge(), obj.getAmountAfterHandlingCharge(), obj.getGst(),
				obj.getGstAmount(), obj.getFinalAmount(), obj.getIsActive(),
				obj.getHandlingChargePercentage() != null ? obj.getHandlingChargePercentage().getTaxPercentage() : 0.0);
	}

	public CurrencyDTO currencyEntityToDto(Currency obj) {
		if (obj == null)
			return null;
		return new CurrencyDTO(obj.getId(), obj.getName(), obj.getCode(), obj.getIsActive(), obj.getCreatedOn());
	}

	public Currency currencyDtoToEntity(CurrencyDTO obj) {
		if (obj == null)
			return null;
		return new Currency(obj.getId(), obj.getName(), obj.getCode(), obj.getIsActive(), obj.getCreatedOn());
	}

	public CountryStateDTO countryStateEntityToDto(CountryState obj) {
		if (obj == null)
			return null;
		return new CountryStateDTO(obj.getId(), obj.getName());
	}

	public CountryState countryStateDtoToEntity(CountryStateDTO obj) {
		if (obj == null)
			return null;
		return new CountryState(obj.getId(), obj.getName());
	}

	public EmployeeDTO employeeEntityToDto(Employee obj) {
		if (obj == null)
			return null;
		return new EmployeeDTO(obj.getId(), obj.getName(), obj.getCode(), obj.getEmailId(), obj.getDob(),
				obj.getContactNo(), obj.getCreatedOn(), obj.getIsActive(), obj.getCompanyId());
	}

	public Employee employeeDtoToEntity(EmployeeDTO obj) {
		if (obj == null)
			return null;
		return new Employee(obj.getId(), obj.getName(), obj.getCode(), obj.getEmailId(), obj.getDob(),
				obj.getContactNo(), obj.getCreatedOn(), obj.getIsActive(), obj.getCompanyId());
	}

	public SiteDTO siteEntityToDto(Site obj) {
		if (obj == null)
			return null;
		return new SiteDTO(obj.getId(), obj.getParentId(), obj.getCode(), obj.getName(), obj.getSubject(),
				obj.getDescription(), obj.getContractNo(), obj.getAddress(), obj.getBillingAddress(), obj.getGstNo(),
				employeeEntityToDto(obj.getContactPerson()), obj.getTdsOnIncomeTax(), obj.getTdsOnLabourCess(),
				obj.getTdsOnGst(), obj.getStartDate(), obj.getEndDate(), currencyEntityToDto(obj.getCurreny()),
				obj.getDieselRetailPrice(), obj.getLubricantsFactor(), obj.getDieselPriceWithLubricants(),
				chainageEntityToDto(obj.getStartChainage()), chainageEntityToDto(obj.getEndChainage()),
				obj.getProjectLength(), countryStateEntityToDto(obj.getState()), obj.getThresholdAmount(),
				obj.getRetention(), obj.getConsultantName(), obj.getConsultantAddress(), obj.getCreatedOn(),
				obj.getIsActive(), obj.getCompanyId());
	}

	public Site siteDtoToEntity(SiteDTO obj) {
		if (obj == null)
			return null;
		return new Site(obj.getId(), obj.getParentId(), obj.getCode(), obj.getName(), obj.getSubject(),
				obj.getDescription(), obj.getContractNo(), obj.getAddress(), obj.getBillingAddress(), obj.getGstNo(),
				employeeDtoToEntity(obj.getContactPerson()), obj.getTdsOnIncomeTax(), obj.getTdsOnLabourCess(),
				obj.getTdsOnGst(), obj.getStartDate(), obj.getEndDate(), currencyDtoToEntity(obj.getCurreny()),
				obj.getDieselRetailPrice(), obj.getLubricantsFactor(), obj.getDieselPriceWithLubricants(),
				chainageDtoToEntity(obj.getStartChainage()), chainageDtoToEntity(obj.getEndChainage()),
				obj.getProjectLength(), countryStateDtoToEntity(obj.getState()), obj.getThresholdAmount(),
				obj.getRetention(), obj.getConsultantName(), obj.getConsultantAddress(), obj.getCreatedOn(),
				obj.getIsActive(), obj.getCompanyId());
	}

	public CompanyDTO companyEntityToDto(Company obj) {
		if (obj == null)
			return null;
		return new CompanyDTO(obj.getId(), obj.getName(), obj.getAddress(), obj.getGstNo(), obj.getRegNo(),
				obj.getCinNo(), obj.getShortName(), obj.getHeaderLogo(), obj.getPrintLogo(),
				obj.getRegisteredOfficeAddress(), obj.getCorporateOfficeAddress(), obj.getEmail(), obj.getPhoneNo(),
				obj.getWebsite(), companyClientEntityToDto(obj.getClient()));
	}

	public Company companyDtoToEntity(CompanyDTO obj) {
		if (obj == null)
			return null;
		return new Company(obj.getId(), obj.getName(), obj.getAddress(), obj.getGstNo(), obj.getRegNo(), obj.getCinNo(),
				obj.getShortName(), obj.getHeaderLogo(), obj.getPrintLogo(), obj.getRegisteredOfficeAddress(),
				obj.getCorporateOfficeAddress(), obj.getEmail(), obj.getPhoneNo(), obj.getWebsite(),
				companyClientDtoToEntity(obj.getClient()));
	}

	public CompanyClient companyClientDtoToEntity(CompanyClientDTO obj) {
		if (obj == null)
			return null;
		return new CompanyClient(obj.getId(), obj.getName(), obj.getAddress(), obj.getIsActive(), obj.getCreatdeOn(),
				obj.getCreatedBy());
	}

	public CompanyClientDTO companyClientEntityToDto(CompanyClient obj) {
		if (obj == null)
			return null;
		return new CompanyClientDTO(obj.getId(), obj.getName(), obj.getAddress(), obj.getIsActive(), obj.getCreatdeOn(),
				obj.getCreatedBy());
	}

	public BillDeduction billDeductionDtoToEntity(BillDeductionDTO obj) {
		if (obj == null)
			return null;
		return new BillDeduction(obj.getId(), obj.getName(), obj.getIsActive(), obj.getCompanyId(), obj.getCreatedOn(),
				obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public BillDeductionDTO billDeductionEntityToDto(BillDeduction obj) {
		if (obj == null)
			return null;
		return new BillDeductionDTO(obj.getId(), obj.getName(), obj.getIsActive(), obj.getCompanyId(),
				obj.getCreatedOn(), obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public BillDeductionMappingDTO billDeductionMappingEntityToDto(BillDeductionMapping obj) {
		if (obj == null)
			return null;
		return new BillDeductionMappingDTO(obj.getId(), obj.getBillId(), billDeductionEntityToDto(obj.getDeduction()),
				obj.getIsPercentage(), obj.getValue(), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy(),
				obj.getModifiedOn(), obj.getModifiedBy());
	}

	public BillDeductionMapping billDeductionMappingDtoToEntity(BillDeductionMappingDTO obj) {
		if (obj == null)
			return null;
		return new BillDeductionMapping(obj.getId(), obj.getBillId(), billDeductionDtoToEntity(obj.getDeduction()),
				obj.getIsPercentage(), obj.getValue(), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy(),
				obj.getModifiedOn(), obj.getModifiedBy());
	}

	public BillDeductionMapTransac billDeductionMappingEntityToTransac(BillDeductionMapping obj) {
		if (obj == null)
			return null;
		return new BillDeductionMapTransac(null, obj.getBillId(), obj.getDeduction().getId(), obj.getIsPercentage(),
				obj.getValue(), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy());
	}

	public BillBoqQuantityItemTransac billBoqQuantityItemEntityToTransac(BillBoqQuantityItem obj) {
		if (obj == null)
			return null;
		return new BillBoqQuantityItemTransac(null, obj.getBillBoqItem().getBillId(),
				obj.getBillBoqItem().getBoq().getId(), obj.getFromChainage().getId(), obj.getToChainage().getId(),
				obj.getQuantity(), obj.getRate(), obj.getSiteId(), obj.getIsActive(), obj.getCreatedOn(),
				obj.getCreatedBy());
	}

	public BillTransac billEntityToTransac(Bill obj) {
		if (obj == null)
			return null;
		return new BillTransac(null, obj.getType().getId(), obj.getBillNo(), obj.getFromDate(), obj.getToDate(),
				obj.getWorkorder().getId(), obj.getState().getId(), obj.getSiteId(), obj.getIsActive(),
				obj.getCreatedOn(), obj.getCreatedBy());
	}

	public BillBoqItem billBoqItemDtoToEntity(BillBoqItemDTO obj) {
		if (obj == null)
			return null;
		return new BillBoqItem(obj.getId(), obj.getBillId(), obj.getStructureTypeId(), boqItemDtoToEntity(obj.getBoq()),
				unitDtoToEntity(obj.getUnit()), obj.getVendorDescription(), obj.getRemark(), obj.getSiteId(),
				obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public BillBoqItemDTO billBoqItemEntityToDto(BillBoqItem obj) {
		if (obj == null)
			return null;
		return new BillBoqItemDTO(obj.getId(), obj.getBillId(), obj.getStructureTypeId(),
				boqItemEntityToDto(obj.getBoq()), unitEntityToDto(obj.getUnit()), obj.getVendorDescription(),
				obj.getRemark(), obj.getSiteId(), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy(),
				obj.getModifiedOn(), obj.getModifiedBy());
	}

	public BillBoqQuantityItem billBoqQuantityItemDtoToEntity(BillBoqQuantityItemDTO obj) {
		if (obj == null)
			return null;
		return new BillBoqQuantityItem(obj.getId(), obj.getBillBoqItemId(), chainageDtoToEntity(obj.getFromChainage()),
				chainageDtoToEntity(obj.getToChainage()), obj.getQuantity(), obj.getRate(), obj.getSiteId(), null,
				obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public BillBoqQuantityItemDTO billBoqQuantityItemEntityToDto(BillBoqQuantityItem obj) {
		if (obj == null)
			return null;
		return new BillBoqQuantityItemDTO(obj.getId(), obj.getBillBoqItemId(),
				chainageEntityToDto(obj.getFromChainage()), chainageEntityToDto(obj.getToChainage()), obj.getQuantity(),
				obj.getRate(), obj.getSiteId(), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy(),
				obj.getModifiedOn(), obj.getModifiedBy());
	}

	public S3FileDTO s3FileEntityToDto(S3File obj) {
		if (obj == null)
			return null;
//		String encodedBase64File = utilityService.getFileS3EncodedBase64(obj.getId());
		String encodedBase64File = null;
		S3FileDTO file = new S3FileDTO(obj.getId(), obj.getName(), obj.getModifiedName(), obj.getPath(),
				obj.getBucket(), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy(), obj.getModuleId(),
				obj.getSiteId(), obj.getCompanyId());
		file.setEncodedBase64(encodedBase64File);
		return file;
	}

	public S3File s3FileDtoToEntity(S3FileDTO obj) {
		if (obj == null)
			return null;
		return new S3File(obj.getId(), obj.getName(), obj.getModifiedName(), obj.getPath(), obj.getBucket(),
				obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy(), obj.getModuleId(), obj.getSiteId(),
				obj.getCompanyId());
	}

	public SubcategoryItem subcategoryItemDtoToEntity(SubcategoryItemDTO obj) {
		if (obj == null)
			return null;
		return new SubcategoryItem(obj.getId(), obj.getCode(), obj.getStandardBookCode(), obj.getName(),
				obj.getCreatedOn(), obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getIsActive(),
				obj.getCompanyId());
	}

	public SubcategoryItemDTO subcategoryItemEntityToDto(SubcategoryItem obj) {
		if (obj == null)
			return null;
		return new SubcategoryItemDTO(obj.getId(), obj.getCode(), obj.getStandardBookCode(), obj.getName(),
				obj.getCreatedOn(), obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getIsActive(),
				obj.getCompanyId());
	}

	public CategoryItemDTO categoryItemEntityToDto(CategoryItem obj) {
		if (obj == null)
			return null;
		return new CategoryItemDTO(obj.getId(), obj.getCode(), obj.getStandardBookCode(), obj.getName(),
				obj.getOfStructure(), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy(), obj.getModifiedOn(),
				obj.getModifiedBy(), obj.getCompanyId());
	}

	public CategoryItem categoryItemDtoToEntity(CategoryItemDTO obj) {
		if (obj == null)
			return null;
		return new CategoryItem(obj.getId(), obj.getCode(), obj.getStandardBookCode(), obj.getName(),
				obj.getOfStructure(), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy(), obj.getModifiedOn(),
				obj.getModifiedBy(), obj.getCompanyId());
	}

	public UnitTypeDTO unitTypeEntityToDto(UnitType obj) {
		if (obj == null)
			return null;
		return new UnitTypeDTO(obj.getId(), obj.getName());
	}

	public UnitType unitTypeDtoToEntity(UnitTypeDTO obj) {
		if (obj == null)
			return null;
		return new UnitType(obj.getId(), obj.getName());
	}

	public UnitDTO unitEntityToDto(Unit obj) {
		if (obj == null)
			return null;
		return new UnitDTO(obj.getId(), obj.getName(), obj.getDescription(), unitTypeEntityToDto(obj.getType()),
				obj.getIsActive(), obj.getCompanyId());
	}

	public Unit unitDtoToEntity(UnitDTO obj) {
		if (obj == null)
			return null;
		return new Unit(obj.getId(), obj.getName(), obj.getDescription(), unitTypeDtoToEntity(obj.getType()),
				obj.getIsActive(), obj.getCompanyId());
	}

	public BoqItemDTO boqItemEntityToDto(BoqItem obj) {
		if (obj == null)
			return null;
		return new BoqItemDTO(obj.getId(), obj.getCode(), obj.getStandardBookCode(), obj.getName(), obj.getName(),
				unitEntityToDto(obj.getUnit()), categoryItemEntityToDto(obj.getCategory()),
				subcategoryItemEntityToDto(obj.getSubcategory()), obj.getIsActive(), obj.getCreatedOn(),
				obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getCompanyId());
	}

	public BoqItem boqItemDtoToEntity(BoqItemDTO obj) {
		if (obj == null)
			return null;
		return new BoqItem(obj.getId(), obj.getCode(), obj.getStandardBookCode(), obj.getName(),
				unitDtoToEntity(obj.getUnit()), categoryItemDtoToEntity(obj.getCategory()),
				subcategoryItemDtoToEntity(obj.getSubcategory()), obj.getIsActive(), obj.getCreatedOn(),
				obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getCompanyId());
	}

	public ChainageTraverse chainageTraverseDtoToEntity(ChainageTraverseDTO obj) {
		if (obj == null)
			return null;
		return new ChainageTraverse(obj.getId(), obj.getName(), obj.getIsActive(), obj.getSiteId(), obj.getCompanyId(),
				obj.getModifiedOn(), obj.getModifiedBy());
	}

	public ChainageTraverseDTO chainageTraverseEntityToDto(ChainageTraverse obj) {
		if (obj == null)
			return null;
		return new ChainageTraverseDTO(obj.getId(), obj.getName(), obj.getIsActive(), obj.getSiteId(),
				obj.getCompanyId(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public Chainage chainageDtoToEntity(ChainageDTO obj) {
		if (obj == null)
			return null;
		return new Chainage(obj.getId(), obj.getName(), obj.getIsActive(),
				chainageTraverseDtoToEntity(obj.getPrevious()), chainageTraverseDtoToEntity(obj.getNext()),
				obj.getSiteId(), obj.getCompanyId(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public ChainageDTO chainageEntityToDto(Chainage obj) {
		if (obj == null)
			return null;
		return new ChainageDTO(obj.getId(), obj.getName(), obj.getIsActive(),
				chainageTraverseEntityToDto(obj.getPrevious()), chainageTraverseEntityToDto(obj.getNext()),
				obj.getSiteId(), obj.getCompanyId(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public BillType billTypeDtoToEntity(BillTypeDTO obj) {
		if (obj == null)
			return null;
		return new BillType(obj.getId(), obj.getCode(), obj.getName(), obj.getIsActive(), obj.getCreatedOn(),
				obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getCompanyId());
	}

	public BillTypeDTO billTypeEntityToDto(BillType obj) {
		if (obj == null)
			return null;
		return new BillTypeDTO(obj.getId(), obj.getCode(), obj.getName(), obj.getIsActive(), obj.getCreatedOn(),
				obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getCompanyId());
	}

	public EngineState engineStateDtoToEntity(EngineStateDTO obj) {
		if (obj == null)
			return null;
		return new EngineState(obj.getId(), obj.getName(), obj.getAlias(), obj.getRgbColorCode(), obj.getButtonText(),
				obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy());
	}

	public EngineStateDTO engineStateEntityToDto(EngineState obj) {
		if (obj == null)
			return null;
		return new EngineStateDTO(obj.getId(), obj.getName(), obj.getAlias(), obj.getRgbColorCode(),
				obj.getButtonText(), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy());
	}

	public ContractorBankDetailDTO contractorBankDetailEntityToDto(ContractorBankDetail obj) {
		if (obj == null)
			return null;
		return new ContractorBankDetailDTO(obj.getId(), obj.getContractorId(), obj.getAccountNo(), obj.getAccountName(),
				obj.getBankName(), obj.getIfscCode(), obj.getAddress(), fileEntityToDto(obj.getCancelChequeFile()),
				obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy());
	}

	public ContractorBankDetail contractorBankDetailDtoToEntity(ContractorBankDetailDTO obj) {
		if (obj == null)
			return null;
		return new ContractorBankDetail(obj.getId(), obj.getContractorId(), obj.getAccountNo(), obj.getAccountName(),
				obj.getBankName(), obj.getIfscCode(), obj.getAddress(), fileDtoToEntity(obj.getCancelChequeFile()),
				obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy());
	}

	public FileDTO fileEntityToDto(FileEntity obj) {
		if (obj == null)
			return null;
		FileDTO file = new FileDTO(obj.getId(), obj.getName(), obj.getPath(), obj.getCreatedOn(), obj.getCreatedBy(),
				obj.getModuleId());
		if (file.getPath() != null && obj.getModule() != null) {
			file.setFullFilePath(obj.getModule().getBaseUrl() + file.getPath());
		}
		return file;
	}

	public FileEntity fileDtoToEntity(FileDTO obj) {
		if (obj == null)
			return null;
		return new FileEntity(obj.getId(), obj.getName(), obj.getPath(), obj.getCreatedOn(), obj.getCreatedBy(),
				obj.getModuleId());
	}

	public ContractorBillingAddressDTO contractorBillingAddressEntityToDto(ContractorBillingAddress obj) {
		if (obj == null)
			return null;
		return new ContractorBillingAddressDTO(obj.getId(), obj.getContractorId(), obj.getAddress(), obj.getGstNo(),
				obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy());
	}

	public ContractorBillingAddress contractorBillingAddressDtoToEntity(ContractorBillingAddressDTO obj) {
		if (obj == null)
			return null;
		return new ContractorBillingAddress(obj.getId(), obj.getContractorId(), obj.getAddress(), obj.getGstNo(),
				obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy());
	}

	public WorkorderContractor workorderContractorDtoToEntity(WorkorderContractorDTO obj) {

		if (obj == null)
			return null;
		return new WorkorderContractor(obj.getId(), obj.getWorkorderId(), obj.getContractorId(),
				contractorBankDetailDtoToEntity(obj.getBankDetail()),
				contractorBillingAddressDtoToEntity(obj.getBillingAddress()), obj.getIsActive(), obj.getModifiedOn(),
				obj.getModifiedBy());
	}

	public WorkorderContractorDTO workorderContractorEntityToDto(WorkorderContractor obj) {

		if (obj == null)
			return null;
		return new WorkorderContractorDTO(obj.getId(), obj.getWorkorderId(), obj.getContractorId(),
				contractorBankDetailEntityToDto(obj.getBankDetail()),
				contractorBillingAddressEntityToDto(obj.getBillingAddress()), obj.getIsActive(), obj.getModifiedOn(),
				obj.getModifiedBy());
	}

	public WorkorderTypeDTO workorderTypeEntityToDto(WorkorderType obj) {
		if (obj == null)
			return null;
		return new WorkorderTypeDTO(obj.getId(), obj.getName(), obj.getCode(), obj.getIsActive(), obj.getModifiedOn(),
				obj.getModifiedBy());
	}

	public WorkorderType workorderTypeDtoToEntity(WorkorderTypeDTO obj) {
		if (obj == null)
			return null;
		return new WorkorderType(obj.getId(), obj.getName(), obj.getCode(), obj.getIsActive(), obj.getModifiedOn(),
				obj.getModifiedBy());
	}

	public ContractorDTO contractorEntityToDto(Contractor obj) {
		if (obj == null)
			return null;
		return new ContractorDTO(obj.getId(), obj.getName(), obj.getGstNo(), obj.getPanNo(), obj.getEmail(),
				obj.getAddress(), obj.getPhoneNo(), obj.getVendorCode(), obj.getCreatedOn(), obj.getIsActive(),
				obj.getCompanyId());
	}

	public Contractor contractorDtoToEntity(ContractorDTO obj) {
		if (obj == null)
			return null;
		return new Contractor(obj.getId(), obj.getName(), obj.getGstNo(), obj.getPanNo(), obj.getEmail(),
				obj.getAddress(), obj.getPhoneNo(), obj.getVendorCode(), obj.getCreatedOn(), obj.getIsActive(),
				obj.getCompanyId());
	}

	public Workorder workorderDtoToEntity(WorkorderDTO obj) {
		if (obj == null)
			return null;
		return new Workorder(obj.getId(), obj.getSubject(), obj.getUniqueNo(), obj.getStartDate(), obj.getEndDate(),
				contractorDtoToEntity(obj.getContractor()), workorderContractorDtoToEntity(obj.getWoContractor()),
				workorderTypeDtoToEntity(obj.getType()), engineStateDtoToEntity(obj.getState()),
				obj.getSystemBillStartDate(), obj.getPreviousBillAmount(), obj.getPreviousBillNo(), obj.getIsActive(),
				obj.getVersion(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getSiteId(), obj.getCompanyId());
	}

	public WorkorderDTO workorderEntityToDto(Workorder obj) {
		if (obj == null)
			return null;
		return new WorkorderDTO(obj.getId(), obj.getSubject(), obj.getUniqueNo(), obj.getStartDate(), obj.getEndDate(),
				contractorEntityToDto(obj.getContractor()), workorderContractorEntityToDto(obj.getWoContractor()),
				workorderTypeEntityToDto(obj.getType()), engineStateEntityToDto(obj.getState()),
				obj.getSystemBillStartDate(), obj.getPreviousBillAmount(), obj.getPreviousBillNo(), obj.getIsActive(),
				obj.getVersion(), obj.getModifiedOn(), obj.getModifiedBy(), obj.getSiteId(), obj.getCompanyId());
	}

	public Bill billDtoToEntity(BillDTO obj) {
		if (obj == null)
			return null;
		return new Bill(obj.getId(), billTypeDtoToEntity(obj.getType()), obj.getBillNo(), obj.getFromDate(),
				obj.getToDate(), workorderDtoToEntity(obj.getWorkorder()), obj.getTaxInvoiceNo(),
				obj.getTaxInvoiceDate(), obj.getApplicableIgst(), obj.getIsIgstOnly(),
				engineStateDtoToEntity(obj.getState()), obj.getSiteId(), obj.getIsActive(), obj.getCreatedOn(),
				obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public BillDTO billEntityToDto(Bill obj) {
		if (obj == null)
			return null;
		return new BillDTO(obj.getId(), billTypeEntityToDto(obj.getType()), obj.getBillNo(), obj.getFromDate(),
				obj.getToDate(), workorderEntityToDto(obj.getWorkorder()), obj.getTaxInvoiceNo(),
				obj.getTaxInvoiceDate(), obj.getApplicableIgst(), obj.getIsIgstOnly(),
				engineStateEntityToDto(obj.getState()), obj.getSiteId(), obj.getIsActive(), obj.getCreatedOn(),
				obj.getCreatedBy(), obj.getModifiedOn(), obj.getModifiedBy());
	}

	public DocumentType documentTypeDtoToEntity(DocumentTypeDTO obj) {
		if (obj == null)
			return null;
		return new DocumentType(obj.getId(), obj.getName(), obj.getDescription(), obj.getIsActive(), obj.getCreatedOn(),
				obj.getCreatedBy(), obj.getCompanyId());
	}

	public DocumentTypeDTO documentTypeEntityToDto(DocumentType obj) {
		if (obj == null)
			return null;
		return new DocumentTypeDTO(obj.getId(), obj.getName(), obj.getDescription(), obj.getIsActive(),
				obj.getCreatedOn(), obj.getCreatedBy(), obj.getCompanyId());
	}

	public BillFileDTO billFilesEntityToDto(BillFile obj) {
		if (obj == null)
			return null;
		return new BillFileDTO(obj.getId(), obj.getBillId(), documentTypeEntityToDto(obj.getType()),
				s3FileEntityToDto(obj.getFile()), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy());
	}

	public BillFile billFilesDtoToEntity(BillFileDTO obj) {
		if (obj == null)
			return null;
		return new BillFile(obj.getId(), obj.getBillId(), documentTypeDtoToEntity(obj.getType()),
				s3FileDtoToEntity(obj.getFile()), obj.getIsActive(), obj.getCreatedOn(), obj.getCreatedBy());
	}

	public WorkorderLabourDepartment workorderLabourDepartmentAddOrUpdateRequestDtoToEntity(
			WorkorderLabourDepartmentAddUpdateRequest obj) {
		if (obj == null)
			return null;
		return new WorkorderLabourDepartment(obj.getId(), obj.getName(), obj.getCompanyId(), true, new Date(),
				obj.getUserId(), new Date(), obj.getUserId());
	}

	public WorkorderLabourDepartmentFetchResponse workorderLabourDepartmentEntityToFetchResponseDto(
			WorkorderLabourDepartment obj) {
		if (obj == null)
			return null;
		return new WorkorderLabourDepartmentFetchResponse(obj.getId(), obj.getName());
	}

	public WorkorderLabourType workorderLabourTypeAddOrUpdateRequestDtoToEntity(
			WorkorderLabourTypeAddUpdateRequest obj) {
		if (obj == null)
			return null;
		return new WorkorderLabourType(obj.getId(), obj.getName(), obj.getCompanyId(), true, new Date(),
				obj.getUserId(), new Date(), obj.getUserId());
	}

	public WorkorderLabourTypeFetchResponse workorderLabourTypeEntityToFetchResponseDto(WorkorderLabourType obj) {
		if (obj == null)
			return null;
		return new WorkorderLabourTypeFetchResponse(obj.getId(), obj.getName());
	}

	public WorkorderLabourDesignation workorderLabourDesignationAddOrUpdateRequestDtoToEntity(
			WorkorderLabourDesignationAddUpdateRequest obj) {
		if (obj == null)
			return null;
		return new WorkorderLabourDesignation(obj.getId(), obj.getName(), obj.getCompanyId(), true, new Date(),
				obj.getUserId(), new Date(), obj.getUserId());
	}

	public WorkorderLabourDesignationFetchResponse workorderLabourDesignationEntityToFetchResponseDto(
			WorkorderLabourDesignation obj) {
		if (obj == null)
			return null;
		return new WorkorderLabourDesignationFetchResponse(obj.getId(), obj.getName());
	}

	public WorkorderLabour workorderLabourAddOrUpdateRequestDtoToEntity(WorkorderLabourAddUpdateRequest obj) {
		if (obj == null)
			return null;
		return new WorkorderLabour(obj.getId(), obj.getCode(), obj.getName(),
				obj.getDepartmentId() != null ? new WorkorderLabourDepartment(obj.getDepartmentId()) : null,
				obj.getDesignationId() != null ? new WorkorderLabourDesignation(obj.getDesignationId()) : null,
				obj.getTypeId() != null ? new WorkorderLabourType(obj.getTypeId()) : null, obj.getSiteId(),
				obj.getFromDate(), obj.getToDate(), true, new Date(), obj.getUserId(), new Date(), obj.getUserId());
	}

	public WorkorderLabourFetchResponse workorderLabourEntityToFetchResponseDto(WorkorderLabour obj) {
		if (obj == null)
			return null;
		return new WorkorderLabourFetchResponse(obj.getId(), obj.getCode(), obj.getName(),
				obj.getDepartment() != null ? obj.getDepartment().getId() : null,
				obj.getDepartment() != null ? obj.getDepartment().getName() : null,
				obj.getDesignation() != null ? obj.getDesignation().getId() : null,
				obj.getDesignation() != null ? obj.getDesignation().getName() : null,
				obj.getType() != null ? obj.getType().getId() : null,
				obj.getType() != null ? obj.getType().getName() : null, obj.getFromDate(), obj.getToDate());
	}

	public ClientInvoice clientInvoiceAddOrUpdateRequestDtoToEntity(ClientInvoiceAddUpdateRequest obj) {
		if (obj == null)
			return null;
		return new ClientInvoice(obj.getId(), obj.getInvoiceType(), obj.getSupplyType(), null, obj.getInvoiceDate(),
				obj.getSupplyDate(), new CountryState(obj.getSupplyStateId()), obj.getTermsAndConditions(),
				obj.getReverseCharge(),
				obj.getTransportMode() != null ? new TransportMode(obj.getTransportMode()) : null, obj.getVehicleNo(),
				new EngineState(obj.getStateId()), new ClientImplementationUnit(obj.getCiuId()),
				new City(obj.getBillingCityId()), obj.getBillingAddress(), obj.getBillingZipCode(),
				new City(obj.getShippingCityId()), obj.getShippingAddress(), obj.getShippingZipCode(),
				obj.getDispatchCityId(), obj.getDispatchAddress(), obj.getDispatchZipCode(), obj.getGstNo(),
				obj.getRemarks(), obj.getSiteId(), obj.getCompanyId(), true, new Date(), obj.getUpdatedBy());
	}

	public ClientInvoiceProduct clientInvoiceProductAddOrUpdateRequestDtoToEntity(ClientInvoiceProductAddRequest obj) {
		if (obj == null)
			return null;
		return new ClientInvoiceProduct(obj.getId(), obj.getClientInvoiceId(),
				new GstManagement(obj.getGstManagementId()), obj.getAmount(), obj.getIsIgst(),
				obj.getApplicableCgstPercentage(), obj.getApplicableCgstAmount(), obj.getApplicableSgstPercentage(),
				obj.getApplicableSgstAmount(), obj.getRemark(), new Unit(obj.getUnitId()), obj.getQuantity(), true,
				new Date(), obj.getUpdatedBy());
	}

	public ClientInvoiceFetchResponse clientInvoiceEntityToFetchResponseDto(ClientInvoice obj) {
		if (obj == null)
			return null;
		return new ClientInvoiceFetchResponse(obj.getId(), obj.getInvoiceType(), obj.getSupplyType(),
				obj.getInvoiceNo(), obj.getInvoiceDate(), obj.getSupplyDate(),
				obj.getSupplyState() != null ? obj.getSupplyState().getId() : null,
				obj.getSupplyState() != null ? obj.getSupplyState().getStateCode() : null,
				obj.getSupplyState() != null ? obj.getSupplyState().getName() : null, obj.getTermsAndConditions(),
				obj.getReverseCharge(), obj.getTransportMode() != null ? obj.getTransportMode().getId() : null,
				obj.getTransportMode() != null ? obj.getTransportMode().getName() : null, obj.getVehicleNo(),
				obj.getCiu() != null ? obj.getCiu().getId() : null,
				obj.getCiu() != null ? obj.getCiu().getOfficeName() : null,
				obj.getBillingCity() != null && obj.getBillingCity().getState() != null
						? obj.getBillingCity().getState().getId().intValue()
						: null,
				obj.getBillingCity() != null && obj.getBillingCity().getState() != null
						? obj.getBillingCity().getState().getStateCode()
						: null,
				obj.getBillingCity() != null && obj.getBillingCity().getState() != null
						? obj.getBillingCity().getState().getName()
						: null,
				obj.getBillingCity() != null ? obj.getBillingCity().getId() : null,
				obj.getBillingCity() != null ? obj.getBillingCity().getName() : null, obj.getBillingAddress(),
				obj.getBillingZipCode(),
				obj.getShippingCity() != null && obj.getShippingCity().getState() != null
						? obj.getShippingCity().getState().getId().intValue()
						: null,
				obj.getShippingCity() != null && obj.getShippingCity().getState() != null
						? obj.getShippingCity().getState().getName()
						: null,
				obj.getShippingCity() != null ? obj.getShippingCity().getId() : null,
				obj.getShippingCity() != null ? obj.getShippingCity().getName() : null, obj.getShippingAddress(),
				obj.getShippingZipCode(),
				obj.getDispatchCity() != null && obj.getDispatchCity().getState() != null
						? obj.getDispatchCity().getState().getId().intValue()
						: null,
				obj.getDispatchCity() != null && obj.getDispatchCity().getState() != null
						? obj.getDispatchCity().getState().getStateCode()
						: null,
				obj.getDispatchCity() != null && obj.getDispatchCity().getState() != null
						? obj.getDispatchCity().getState().getName()
						: null,
				obj.getDispatchCity() != null ? obj.getDispatchCity().getId() : null,
				obj.getDispatchCity() != null ? obj.getDispatchCity().getName() : null, obj.getDispatchAddress(),
				obj.getDispatchZipCode(), obj.getGstNo(), null, null, 0.0, 0.0, 0.0, 0.0, 0.0, null,
				obj.getSite() != null ? obj.getSite().getId() : null,
				obj.getSite() != null ? obj.getSite().getName() : null,
				obj.getState() != null ? obj.getState().getId() : null,
				obj.getState() != null ? obj.getState().getName() : null,
				obj.getState() != null ? obj.getState().getAlias() : null,
				obj.getState() != null ? obj.getState().getRgbColorCode() : null,
				obj.getState() != null ? obj.getState().getButtonText() : null, obj.getRemarks(), obj.getUpdatedOn(),
				obj.getUpdatedByDetail().getName());
	}

	public ClientInvoiceStateTransitionFetchResponse clientInvoiceStateTransitionEntityToDto(
			ClientInvoiceStateTransitionMappingV2 obj) {
		if (obj == null)
			return null;
		return new ClientInvoiceStateTransitionFetchResponse(obj.getId(), obj.getState().getId(),
				obj.getState().getName(), obj.getCreatedOn(), obj.getCreatedByUser().getId(),
				obj.getCreatedByUser().getName(), obj.getCreatedByUser().getCode());
	}

	public ClientInvoiceIrnInfoResponse clientInvoiceIrnInfoEntityToFetchResponseDto(ClientInvoiceIrnInfo obj) {
		if (obj == null)
			return null;
		return new ClientInvoiceIrnInfoResponse(obj.getAckNo(), obj.getAckDate(), obj.getIrn(), obj.getSignedInvoice(),
				obj.getSignedQrCode(), obj.getCancelDate(),
				obj.getCancelReason() != null ? obj.getCancelReason().getName() : null, obj.getCancelRemark());
	}

	public BillDeductionResponseDto billDeductionMappingEntitytoBillDeductionResponseDto(BillDeductionMappingV2 obj) {
		if (obj == null)
			return null;
		return new BillDeductionResponseDto(obj.getId(), obj.getBillDeduction().getId(),
				obj.getBillDeduction().getName(), obj.getIsPercentage(), obj.getValue());
	}

}
