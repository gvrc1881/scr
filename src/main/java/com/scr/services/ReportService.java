package com.scr.services;

import java.util.List;
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
import com.scr.model.ElementarySection;
import com.scr.model.Facility;
import com.scr.model.Failure;
import com.scr.model.FailureAnalysis;
import com.scr.model.FunctionalLocationTypes;
import com.scr.model.GeographicState;
import com.scr.model.MajorSections;
import com.scr.model.Make;
import com.scr.model.Model;
import com.scr.model.ObservationCategory;
import com.scr.model.ObservationsCheckList;
import com.scr.model.PbSwitchControl;
import com.scr.model.PowerBlock;
import com.scr.model.PrecautionaryMeasuresMaster;
import com.scr.model.ProductCategoryMember;
import com.scr.model.ProductMakeModelAssociation;
import com.scr.model.ReportParameter;
import com.scr.model.ReportRepository;
import com.scr.model.Section;
import com.scr.model.StatusItem;
import com.scr.model.Stipulations;
import com.scr.model.SubDivision;
import com.scr.model.TpcBoard;
import com.scr.model.Uom;
import com.scr.model.Zone;
import com.scr.repository.ReportParametersRepository;
import com.scr.repository.ReportRepositoryRepository;
import com.scr.repository.SectionRepository;
import com.scr.repository.StatusItemRepository;
import com.scr.repository.StipulationRepository;
import com.scr.repository.SubDivisionRepository;
import com.scr.repository.TPCBoardRepository;
import com.scr.repository.UomRepository;
import com.scr.repository.ZoneRepository;
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
import com.scr.repository.MajorSectionRepository;
import com.scr.repository.MakeRepository;
import com.scr.repository.ModelRepository;
import com.scr.repository.ObservationCategoryRepository;
import com.scr.repository.ObservationCheckListRepository;
import com.scr.repository.PBSwitchControlRepository;
import com.scr.repository.PowerBlockRepository;
import com.scr.repository.PrecautionaryMeasureMasterRepository;
import com.scr.repository.ProductCategoryMemberRepository;
import com.scr.repository.ProductMakeModelAssocRepository;


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
	
	public List<ReportRepository> findAllReportNames(String reportType) {	
		return reportRepositoryRepository.findByReportCategory(reportType);
	}
	public List<ProductCategoryMember> findAllAssetTypes(String assetType) {	
		return productCategoryMemberRepository.findByProductCategoryId(assetType);
	}
	public List<ReportParameter> findall() {	
		return reportParametersRepository.findAll();
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
	public List<ElementarySection>findAllElementarySection(String facilityId) {	
		   return elementarySectionsRepository.findByFacilityId(facilityId);
		}
	public List<ObservationCategory>findDepartments() {	
		   return observationCategoryRepository.findByInspectionTypeOrderByObservationCategoryAsc();
		}
	public List<ObservationsCheckList>findObservationCheckList() {	
		   return observationCheckListRepository.findAll();
		}
	
	public ReportRequest generateReport(ReportRequest report) {
		report= reportResource.generateReport(report);
		return report;
	}
	
	public List<AssetScheduleAssoc> findAllScheduleCodes(String assetType) {
		List<AssetScheduleAssoc> assetSche = assetSchAssoRepository.findByAssetType(assetType);
		return assetSche;
	}
	
	public List<AssetsScheduleHistory> findAssetIdScheduleCodes(String assetType,String scheduleCode) {
		List<AssetsScheduleHistory> assetId = assetsScheduleHistoryRepository.findByAssetTypeAndScheduleCodeOrderByScheduleCodeAsc(assetType,scheduleCode);
		return assetId;
	}
	public List<Division> findDivision(Zone zoneId) {
		List<Division> divisionCode = divisionRepository.findByZoneIdOrderByCodeAsc(zoneId);
		return divisionCode;
	}
	public List<SubDivision> findSubDivision(Division divisionId) {
		List<SubDivision> subDivisionCode = subDivisionRepository.findByDivisionIdOrderByCodeAsc(divisionId);
		log.info("subDivisionCode"+subDivisionCode);
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
	public List<StatusItem> findAllStatusItem(String statusType){	
		return statusItemRepository.findByStatusTypeId(statusType);
	}
	

	public List<Facility> getFacilitysBasedOnDepotType(String depotType) {
			// TODO Auto-generated method stub
			return facilityRepository.findByDepotTypeOrderByFacilityNameAsc(depotType);
		}
	public List<Facility> findByDepotTypeOrderByFacilityNameAsc() {
		List<Facility> facilityNames = facilityRepository.findByDepotTypeOrderByFacilityNameAsc("OHE");
		log.info("faciliNameOheDepot"+facilityNames);
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
	public List<AssetMasterData> findAssetIdAndFacilityId(String assetId,String facilityId) {
		List<AssetMasterData> assetType = assetMastersRepository.findByAssetIdAndFacilityId(assetId,facilityId);
		return assetType;
	}
	public List<AssetMasterData> findAssetTypeAndFacilityId(String assetType,String facilityId) {
		List<AssetMasterData> assetId = assetMastersRepository.findByAssetTypeAndFacilityId(assetType,facilityId);
		return assetId;
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
	
	
}
