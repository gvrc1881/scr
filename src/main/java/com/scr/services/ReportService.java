package com.scr.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.jobs.ReportResource;
import com.scr.message.request.ReportRequest;
import com.scr.model.AssetMasterData;
import com.scr.model.AssetScheduleAssoc;
import com.scr.model.AssetsScheduleHistory;
import com.scr.model.CrsEigInspections;
import com.scr.model.Division;
import com.scr.model.ElectricEnergySuppliers;
import com.scr.model.ElectrificationTargets;
import com.scr.model.ElementarySections;
import com.scr.model.Facility;
import com.scr.model.Failure;
import com.scr.model.FailureAnalysis;
import com.scr.model.FunctionalLocationTypes;
import com.scr.model.GeographicState;
import com.scr.model.InspectionType;
import com.scr.model.MajorSections;
import com.scr.model.Make;
import com.scr.model.MeasureOrActivityList;
import com.scr.model.Model;
import com.scr.model.ObservationCategory;
import com.scr.model.ObservationsCheckList;
import com.scr.model.PbSwitchControl;
import com.scr.model.PowerBlock;
import com.scr.model.PrecautionaryMeasuresMaster;
import com.scr.model.ProductCategoryMember;
import com.scr.model.ProductCategoryType;
import com.scr.model.ProductMakeModelAssociation;
import com.scr.model.ReportParameter;
import com.scr.model.ReportRepository;
import com.scr.model.Section;
import com.scr.model.StandardPhaseActivity;
import com.scr.model.StandardPhases;
import com.scr.model.StatusItem;
import com.scr.model.Stipulations;
import com.scr.model.SubDivision;
import com.scr.model.TpcBoard;
import com.scr.model.TpcBoardReportingFacility;
import com.scr.model.TssFeederMaster;
import com.scr.model.Uom;
import com.scr.model.UserDefualtFacConsIndEtc;
import com.scr.model.Zone;
import com.scr.model.Product;
import com.scr.repository.AssetMastersRepository;
import com.scr.repository.AssetSchAssoRepository;
import com.scr.repository.AssetsScheduleHistoryRepository;
import com.scr.repository.CrsEigInspectionRepository;
import com.scr.repository.DivisionRepository;
import com.scr.repository.ElectricEnergySuppliersRepository;
import com.scr.repository.ElectrificationTargetsRepository;
import com.scr.repository.ElementarySectionsRepository;
import com.scr.repository.FacilityRepository;
import com.scr.repository.FailureAnalysisRepository;
import com.scr.repository.FailuresRepository;
import com.scr.repository.FunctionLocationTypesRepository;
import com.scr.repository.GeographicStateRepository;
import com.scr.repository.InspectionTypeRepository;
import com.scr.repository.MajorSectionRepository;
import com.scr.repository.MakeRepository;
import com.scr.repository.MeasureOrActivityListRepository;
import com.scr.repository.ModelRepository;
import com.scr.repository.ObservationCategoryRepository;
import com.scr.repository.ObservationCheckListRepository;
import com.scr.repository.PBSwitchControlRepository;
import com.scr.repository.PowerBlockRepository;
import com.scr.repository.PrecautionaryMeasureMasterRepository;
import com.scr.repository.ProductCategoryMemberRepository;
import com.scr.repository.ProductCategoryTypeRepository;
import com.scr.repository.ProductMakeModelAssocRepository;
import com.scr.repository.ReportParametersRepository;
import com.scr.repository.ReportRepositoryRepository;
import com.scr.repository.SectionRepository;
import com.scr.repository.StandardPhaseActivityRepository;
import com.scr.repository.StandardPhasesRepository;
import com.scr.repository.StatusItemRepository;
import com.scr.repository.StipulationRepository;
import com.scr.repository.SubDivisionRepository;
import com.scr.repository.TPCBoardDepotAssocRepository;
import com.scr.repository.TPCBoardRepository;
import com.scr.repository.UomRepository;
import com.scr.repository.UserDefualtFacConsIndEtcRepository;
import com.scr.repository.ZoneRepository;
import com.scr.repository.ProductRepository;


@Service
public class ReportService {
	
	static Logger log = LogManager.getLogger(ReportService.class);
	
	@Autowired
	private ReportResource reportResource;
	@Autowired
    private ReportParametersRepository reportParametersRepository;
	@Autowired
	private FacilityRepository facilityRepository;
	@Autowired
	private FailuresRepository failuresRepository;
	@Autowired
	private PowerBlockRepository powerBlockRepository;
	@Autowired
	private ProductCategoryMemberRepository productCategoryMemberRepository;
	@Autowired
	private PBSwitchControlRepository pbSwitchControlRepository;
	@Autowired
	private ElementarySectionsRepository elementarySectionsRepository;
	@Autowired
	private AssetsScheduleHistoryRepository assetsScheduleHistoryRepository;
	@Autowired
	private ReportRepositoryRepository reportRepositoryRepository;
	@Autowired
	private AssetSchAssoRepository assetSchAssoRepository;
    @Autowired
    private ObservationCategoryRepository observationCategoryRepository;
    @Autowired
    private ObservationCheckListRepository observationCheckListRepository;
    @Autowired
    private ZoneRepository zoneRepository;
    @Autowired
    private DivisionRepository divisionRepository;
   @Autowired
   private SubDivisionRepository subDivisionRepository;
   @Autowired
   private FunctionLocationTypesRepository functionLocationTypesRepository;
   @Autowired
   private StipulationRepository stipulationRepository;
   @Autowired
   private UomRepository uomRepository;
   @Autowired
   private SectionRepository sectionRepository;
   @Autowired
   private StatusItemRepository statusItemRepository;
   @Autowired
   private CrsEigInspectionRepository crsEigInspectionRepository;
   @Autowired
   private FailureAnalysisRepository failureAnalysisRepository;
   @Autowired
   private ElectrificationTargetsRepository electrificationTargetsRepository;
	@Autowired
	private PrecautionaryMeasureMasterRepository precautionaryMeasureMasterRepository;
	@Autowired
	private TPCBoardRepository tpcBoardRepository;
	@Autowired
	private ProductMakeModelAssocRepository productMakeModelAssocRepository;
	@Autowired
	private MajorSectionRepository majorSectionRepository;
	@Autowired
	private MakeRepository makeRepository;
	@Autowired
	private ModelRepository modelRepository;
	@Autowired
	private AssetMastersRepository assetMastersRepository;
	@Autowired
	private GeographicStateRepository geographicStateRepository;
	@Autowired
	private ElectricEnergySuppliersRepository electricEnergySuppliersRepository;
	@Autowired
	private InspectionTypeRepository inspectionTypeRepository;
	@Autowired
	private MeasureOrActivityListRepository measureOrActivityListRepository;
	@Autowired
	private TPCBoardDepotAssocRepository tpcBoardDepotAssocRepository;
	
	@Autowired
	private UserDefualtFacConsIndEtcRepository userDefualtFacConsIndEtcRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductCategoryTypeRepository productCategoryTypeRepository;
	
	@Autowired
	private TssFeederMasterService tssFeederMasterService;
	
	@Autowired
	private StandardPhasesRepository standardPhasesRepository;
	
	@Autowired
	private StandardPhaseActivityRepository standardPhaseActivityRepository;
	
	public List<ReportRepository> findAllReportNames(String reportType) {	
		return reportRepositoryRepository.findByReportCategory(reportType);
	}
	public List<ProductCategoryMember> findAllAssetTypes(String assetType) {	
		return productCategoryMemberRepository.findByProductCategoryId(assetType);
	}
	public List<ProductCategoryMember> findByProductId(String productCategoryId) {	
		return productCategoryMemberRepository.findByProductId(productCategoryId);
	}
	
	public List<ReportParameter> findall() {	
		return reportParametersRepository.findAll();
	}
	public List<ReportParameter> findByReportId(String reportId) {	
		return reportParametersRepository.findByReportId(reportId);
	}
	public List<Facility> findAllOrderByFacilityNameAsc() {	
	   return facilityRepository.findAllOrderByFacilityNameAsc();
	}
	public List<Zone> findAllOrderByCodeAsc1() {	
		   List<Zone> zoneList= zoneRepository.findAllOrderByCodeAsc();
		   return zoneList;
		}
	public List<Failure> findAllFailures(){
		return failuresRepository.findAll();
	}
	public List<PowerBlock> findAllPowerBlocks(){
		return powerBlockRepository.findAll();
	}
	
	
	public List<PbSwitchControl> findAllPBSwitch() {	
		   return pbSwitchControlRepository.findAll();
		}
	public List<ElementarySections>findAllElementarySection(String facilityId) {	
		   return elementarySectionsRepository.findByFacilityId(facilityId);
		}
	public List<ObservationCategory>findDepartments() {	
		   return observationCategoryRepository.findAllOrderByObservationCategoryAsc();
		}
	public List<ObservationsCheckList>findObservationCheckList() {	
		   return observationCheckListRepository.findAll();
		}
	
	public List<InspectionType>findAllOrderByInspectionTypeAsc() {	
		   return inspectionTypeRepository.findAllOrderByInspectionTypeAsc();
		}
	
	public ReportRequest generateReport(ReportRequest report) {
		report= reportResource.generateReport(report);
		return report;
	}
	
	public List<AssetScheduleAssoc> findAllScheduleCodes(String assetType) {
		List<AssetScheduleAssoc> assetSche = assetSchAssoRepository.findByAssetType(assetType);
		log.info("assetSchesize"+assetSche.size());
		log.info("assetSche"+assetSche);

		return assetSche;
	}
	public List<AssetScheduleAssoc> findByAssetType(String assetType) {
		List<AssetScheduleAssoc> scheduleCodes = assetSchAssoRepository.findByAssetType(assetType);
		return scheduleCodes;
	}
	
	public List<AssetsScheduleHistory> findAssetIdScheduleCodes(String assetType,String scheduleCode) {
		List<AssetsScheduleHistory> assetId = assetsScheduleHistoryRepository.findDistinctScheduleCodeByAssetTypeAndScheduleCodeOrderByScheduleCodeAsc(assetType,scheduleCode);
		return assetId;
	}
	public List<Division> findDivision(Zone zoneId) {
		List<Division> divisionCode = divisionRepository.findByZoneIdOrderByCodeAsc(zoneId);
		return divisionCode;
	}
	public List<SubDivision> findSubDivision(Division divisionId) {
		List<SubDivision> subDivisionCode = subDivisionRepository.findByDivisionIdOrderByCodeAsc(divisionId);
		return subDivisionCode;
	}
	public List<Facility> findFacilityNames(String subDivision) {
		List<Facility> facilityNames = facilityRepository.findBySubDivision(subDivision);
		return facilityNames;
	}
	
	    public List<FunctionalLocationTypes> findAllOrderByCodeAsc() {
	        return functionLocationTypesRepository.findAllOrderByCodeAsc();
	    }
	    
	 
	    
	public List<Stipulations>findAllStipulationDetails() {	
		   return stipulationRepository.findAll();
		}
	public List<Uom>findAllUomDetails() {	
		   return uomRepository.findAll();
		}
	public List<Section>findAllSectionDetails() {	
		   return sectionRepository.findAll();
		}
	public List<StatusItem> findAllStatusItem(String statusTypeId){	
		return statusItemRepository.findByStatusTypeId(statusTypeId);
	}
	

	public List<Facility> getFacilitysBasedOnDepotType(String depotType) {
			
			return facilityRepository.findByDepotTypeOrderByFacilityNameAsc(depotType);
		}
	public List<Facility> findByDepotTypeOrderByFacilityNameAsc() {
		List<Facility> facilityNames = facilityRepository.findByDepotTypeOrderByFacilityNameAsc("OHE");
		return facilityNames;		
	}
	public List<Facility> findWarehouseFacilityNames() {
		List<Facility> facilityNames = facilityRepository.findByDepotTypeOrderByFacilityNameAsc("WAREHOUSE");
		return facilityNames;		
	}
	public List<Facility> findByDepotType(String depotType) {
		List<Facility> facilityNames = facilityRepository.findByDepotType(depotType);
		return facilityNames;		
	}
	public List<CrsEigInspections>findcrsEigInspection() {	
		   return crsEigInspectionRepository.findAll();
		}
	public List<FailureAnalysis>findFailureAnalysis() {	
		   return failureAnalysisRepository.findAll();
		}
	public List<ElectrificationTargets>findElectrificationTargets() {	
		   return electrificationTargetsRepository.findAll();
		}
	public List<PrecautionaryMeasuresMaster>findPrecautionaryMeasures() {	
		   return precautionaryMeasureMasterRepository.findAll();
		}
	public List<TpcBoard>findTPCBoard() {	
		   return tpcBoardRepository.findAll();
		}
	public List<ProductMakeModelAssociation>findProductMakeModelAssoc() {	
		   return productMakeModelAssocRepository.findAll();
		}
	
	public List<MajorSections>findMajorSection() {	
		   return majorSectionRepository.findAll();
		}
	public List<Make>findMakeDetails() {	
		   return makeRepository.findAll();
		}
	public List<Model>findModelDetails() {	
		   return modelRepository.findAll();
		}
	
	public List<AssetMasterData>findAssetMasterDetails() {	
		   return assetMastersRepository.findAll();
		}
	public List<GeographicState>findGeographicState() {	
		   return geographicStateRepository.findAll();
		}
	public List<ElectricEnergySuppliers>findElectricEnergySuppliers() {	
		   return electricEnergySuppliersRepository.findAll();
		}
	public List<Division> findByOrderByCodeAsc() {	
		   return divisionRepository.findAllOrderByCodeAsc();
		}
		
	public List<ProductCategoryMember>findAll() {	
		   return productCategoryMemberRepository.findAll();
		}
	
	public List<ObservationsCheckList> getObservationCheckListBasedOnObservationCate(String observationCategory) {
		
		return observationCheckListRepository.findByObservationCategory(observationCategory);
	}
	public List<MeasureOrActivityList> getActivityNameBasedOnActivityType(String activityType) {
		
		return measureOrActivityListRepository.findByActivityType(activityType);
	}
	public Optional<Facility> findByFacilityId(String facilityId) {
		
		return facilityRepository.findByFacilityId(facilityId);
	}
	public List<Facility> getFacilityNameBasedOnZone(String zone) {
		List<Facility> facilityNames = facilityRepository.findByZone(zone);
		return facilityNames;
	}
	public List<Facility> findFacilityNameBasedOnZoneAndDepotType(String zone,String depotType) {
		List<Facility> fnZoneAndDepotType = facilityRepository.findByZoneAndDepotType(zone,depotType);
		return fnZoneAndDepotType;
	}
	public List<Facility> findFacilityNameBasedOnDivisionAndDepotType(String division,String depotType) {
		List<Facility> fnDivisionAndDepotType = facilityRepository.findByDivisionAndDepotType(division,depotType);
		return fnDivisionAndDepotType;
	}
	public List<Facility> findFacilityNameBasedOnSubDivisionAndDepotType(String subDivision,String depotType) {
		List<Facility> fnSubDivisionAndDepotType = facilityRepository.findBySubDivisionAndDepotType(subDivision,depotType);
		return fnSubDivisionAndDepotType;
	}
	public List<TpcBoardReportingFacility> getFacilityNameBasedOnTpcBoard(String tpcBoard) {
		List<TpcBoardReportingFacility> facilityNames = tpcBoardDepotAssocRepository.findByTpcBoard(tpcBoard);
		return facilityNames;
	}
	public List<TpcBoardReportingFacility> findFacilityNameBasedOnTpcBoardAndUnitType(String tpcBoard,String unitType) {
		List<TpcBoardReportingFacility> fnByTpcBoardAndUnitType = tpcBoardDepotAssocRepository.findByTpcBoardAndUnitType(tpcBoard,unitType);
		return fnByTpcBoardAndUnitType;
	}
	public List<Failure> findByTypeOfFailureAndFromDateTimeAndThruDateTime(String typeOfFailure,Timestamp fromDateTime,Timestamp thruDateTime) {
		List<Failure> failuresList = failuresRepository.findByTypeOfFailureAndFromDateTimeGreaterThanAndThruDateTimeLessThan(typeOfFailure,fromDateTime,thruDateTime);
		return failuresList;
	}
	public List<Failure> findByTypeOfFailureAndFromDateTimeAndThruDateTimeAndFacilityId(String typeOfFailure,Timestamp fromDateTime,Timestamp thruDateTime,String facilityId) {
		List<Failure> failureRespList = failuresRepository.findByTypeOfFailureAndFromDateTimeGreaterThanAndThruDateTimeLessThanAndFacilityId(typeOfFailure,fromDateTime,thruDateTime,facilityId);
		return failureRespList;
	}
	public List<Failure> findByTypeOfFailureAndFromDateTimeAndThruDateTimeAndDataDiv(String typeOfFailure,Timestamp fromDateTime,Timestamp thruDateTime,String dataDiv) {
		List<Failure> failureRespList = failuresRepository.findByTypeOfFailureAndFromDateTimeGreaterThanAndThruDateTimeLessThanAndDataDiv(typeOfFailure,fromDateTime,thruDateTime,dataDiv);
		return failureRespList;
	}
	public List<ElectricEnergySuppliers> findByStateId(String stateId) {
		
		return electricEnergySuppliersRepository.findByStateId(stateId);
	}
public List<PbSwitchControl> findBySwitchType(String switchType) {
		
		return pbSwitchControlRepository.findDistinctSwitchTypeBySwitchType(switchType);
	}
public List<Stipulations> findStipulationsBasedOnInspectionIdAndAssetType(String inspectionId,String assetType) {
	return stipulationRepository.findByInspectionIdAndAssetType(inspectionId,assetType);

}

	public Optional<UserDefualtFacConsIndEtc> getUserDefaultData(String userName) {
		return userDefualtFacConsIndEtcRepository.findByUserLoginId(userName);
	}

	public Optional<Zone> getZoneObject(String zoneName) {
		return zoneRepository.findByCode(zoneName);
	}

	public Optional<Division> getDivisonObject(String divisionName) {
		// TODO Auto-generated method stub
		return divisionRepository.findByCode(divisionName);
	}
	 public List<Product> getProductIdAndDescription() {
		    List<Product> prodList= productRepository.findByProductIdAndDescription();	
		       return prodList;
		    
		  }
	 public List<Product> findByProductIdAndDescription(String productCategoryId) {
		    
		    List<Product> proList=productRepository.findProducts(productCategoryId);	
		    return proList;  
		  
	}

	public Optional<TssFeederMaster> findByFeederId(String feederId) {
		return tssFeederMasterService.findByFeederId(feederId);
	}
	public List<ProductCategoryType>findProductCategoryType() {	
		   return productCategoryTypeRepository.findAllOrderByProductCategoryTypeIdAsc();
		}

	public List<StandardPhases> findAllStandardPhases() {
		return standardPhasesRepository.findAll();
	}
	public List<StandardPhaseActivity> findByStandardPhaseId(List<StandardPhases> standardPhases) {
		return standardPhaseActivityRepository.findByStandardPhaseIdIn(standardPhases);
	}
}