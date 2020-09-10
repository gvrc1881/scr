package com.scr.controller;


import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
import com.scr.services.ReportService;



/**
 * 
 * @author 
 *
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class ReportController {

	static Logger log = LogManager.getLogger(ReportController.class);
	
	@Autowired
	private ReportService reportService;
	
	
	@RequestMapping(value = "/getReportNamesBasedOnReportType/{reportType}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<ReportRepository>> findAllReportNames(@PathVariable("reportType") String reportType){
		List<ReportRepository> reportNamesGroup= reportService.findAllReportNames(reportType);
			return new ResponseEntity<List<ReportRepository>>(reportNamesGroup, HttpStatus.OK);		
	}
	@RequestMapping(value = "/getAssetTypesBasedOnDepotType/{depotType}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<ProductCategoryMember>> findAllAssetTypes(@PathVariable("depotType") String depotType){
		log.info("assetType"+depotType);
		List<ProductCategoryMember> assetTypes = null;
		
		String productCategoryId = null;
		if("OHE_FIXED_ASSET".equals(depotType) || "PSI_FIXED_ASSET".equals(depotType) || "FP".equals(depotType)|| "TowerCar".equals(depotType) || "DASH_BOARD_CATEGORY".equals(depotType)) 
			assetTypes = reportService.findAllAssetTypes(depotType); 
		else 
			assetTypes= reportService.findByProductId(productCategoryId);
		return new ResponseEntity<List<ProductCategoryMember>>(assetTypes, HttpStatus.OK);
}
	
	@RequestMapping(value = "/reportParameterNames", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<ReportParameter>> findall(){
		List<ReportParameter> reportPNames= reportService.findall();
			return new ResponseEntity<List<ReportParameter>>(reportPNames, HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getReportParameterNamesBasedOnReportId/{reportId}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<ReportParameter>> findAllDailyReports(@PathVariable("reportId") String reportId){
		List<ReportParameter> reportParameters= reportService.findByReportId(reportId);
			return new ResponseEntity<List<ReportParameter>>(reportParameters, HttpStatus.OK);		
	}
    @RequestMapping(value = "/getAllFacilityNames", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<Facility>> findAll(){
		List<Facility> facilityNames= reportService.findAllOrderByFacilityNameAsc();
		return new ResponseEntity<List<Facility>>(facilityNames, HttpStatus.OK);	
		
	}
    @RequestMapping(value = "/getAllZonesData", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<Zone>> findAllZoneCodes(){
		List<Zone> zone= reportService.findAllOrderByCodeAsc1();
		return new ResponseEntity<List<Zone>>(zone, HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getAllfailuresData", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<Failure>> findAllFailures(){
		List<Failure> failuresTable= reportService.findAllFailures();
		return new ResponseEntity<List<Failure>>(failuresTable, HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getAllPowerBlocksData", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<PowerBlock>> findAllPowerBlocks(){
		List<PowerBlock> powerBlocks= reportService.findAllPowerBlocks();
		return new ResponseEntity<List<PowerBlock>>(powerBlocks, HttpStatus.OK);	
		
	}
	
	
	@RequestMapping(value = "/getAllPbSwitchControl", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<PbSwitchControl>> findAllPBSwitch(){
		List<PbSwitchControl> pbSwitchControl= reportService.findAllPBSwitch();
		return new ResponseEntity<List<PbSwitchControl>>(pbSwitchControl,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getElementarySectionsBasedOnFacilityId/{facilityId}", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<ElementarySection>> findAllElementarySection(@PathVariable("facilityId") String facilityId){
		List<ElementarySection> elementarySections= reportService.findAllElementarySection(facilityId);
		return new ResponseEntity<List<ElementarySection>>(elementarySections,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getAllObservationCategories", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<ObservationCategory>> findDepartments(){
		List<ObservationCategory> observationCategory= reportService.findDepartments();
		return new ResponseEntity<List<ObservationCategory>>(observationCategory,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getAllObservationCheckList", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<ObservationsCheckList>> findObservationCheckList(){
		List<ObservationsCheckList> observationCategory= reportService.findObservationCheckList();
		return new ResponseEntity<List<ObservationsCheckList>>(observationCategory,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getAllInspectionTypeData", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<InspectionType>> findInspectionDetails(){
		List<InspectionType> inspectionType= reportService.findAllOrderByInspectionTypeAsc();
		return new ResponseEntity<List<InspectionType>>(inspectionType,HttpStatus.OK);	
		
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/generateReport", method = RequestMethod.POST , headers = "Accept=application/json")
	public ReportRequest submitForm(@Valid @RequestBody ReportRequest report ) {
		log.info("the Data is"+report);
		log.info("the Data is"+report.getReportId());
	    log.info("Enter the request.....................sri");
	    report = reportService.generateReport(report);
		return report;
		
	}
	
	@RequestMapping(value = "/getscheduleCodesBasedonAssetType" ,method = RequestMethod.POST , headers= "accept=application/json")
	public ResponseEntity<List<AssetScheduleAssoc>> getSchCodeBasedOnAssetType(@RequestBody ProductCategoryMember productCategoryMemObj){
		List<AssetScheduleAssoc> assetSch = reportService.findAllScheduleCodes(productCategoryMemObj.getProductId());
		return new ResponseEntity<List<AssetScheduleAssoc>>(assetSch,HttpStatus.OK);
		
	}
	@RequestMapping(value = "/getscheduleCodesBasedonAssetType/{assetType}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<AssetScheduleAssoc>> getSchCodeBasedOnAssetType(@PathVariable("assetType") String assetType){
		List<AssetScheduleAssoc> assetSch= reportService.findAllScheduleCodes(assetType);
		log.info("scheduleCode"+assetSch);
		log.info("scheduleCodeSize"+assetSch.size());
			return new ResponseEntity<List<AssetScheduleAssoc>>(assetSch, HttpStatus.OK);		
	}
	@RequestMapping(value = "/getAssetIdBasedOnScheduleCodesAndAssetTypes" ,method = RequestMethod.POST , headers= "accept=application/json")
	public ResponseEntity<List<AssetsScheduleHistory>> getAssetIdBasedOnScheduleCodesAndAssetTypes(@RequestBody AssetsScheduleHistory assetsScheHistObj){
		List<AssetsScheduleHistory> scheduleCodes = reportService.findAssetIdScheduleCodes( assetsScheHistObj.getAssetType(), assetsScheHistObj.getScheduleCode());
		return new ResponseEntity<List<AssetsScheduleHistory>>(scheduleCodes,HttpStatus.OK);
		
	}
	@RequestMapping(value = "/getAssetIdBasedOnScheduleCodesAndAssetTypes/{assetType}/{scheduleCode}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<AssetsScheduleHistory>> findAssetIdAndScheduleCode(@PathVariable("assetType") String assetType ,@PathVariable("scheduleCode") String scheduleCode){
		List<AssetsScheduleHistory> assetIdsList= reportService.findAssetIdScheduleCodes(assetType, scheduleCode);
			return new ResponseEntity<List<AssetsScheduleHistory>>(assetIdsList, HttpStatus.OK);		
	}
	@RequestMapping(value = "/getDivisionBasedOnZone" ,method = RequestMethod.POST , headers= "accept=application/json")
	public ResponseEntity<List<Division>> getDivisionBasedOnZone(@RequestBody Zone code){
		List<Division> DivisionCodes = reportService.findDivision( code);
		return new ResponseEntity<List<Division>>(DivisionCodes,HttpStatus.OK);
		
	}
	@RequestMapping(value = "/getDivisionBasedOnZone/{zoneId}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<Division>> findDivision(@PathVariable("zoneId") Zone zoneId){
		List<Division> DivisionCodes= reportService.findDivision(zoneId);
			return new ResponseEntity<List<Division>>(DivisionCodes, HttpStatus.OK);		
	}
	@RequestMapping(value = "/getSubDivisionBasedOnDivision" ,method = RequestMethod.POST , headers= "accept=application/json")
	public ResponseEntity<List<SubDivision>> getSubDivisionBasedOnDivision(@RequestBody Division code){
		List<SubDivision> subDivisionCodes = reportService.findSubDivision( code);
		return new ResponseEntity<List<SubDivision>>(subDivisionCodes,HttpStatus.OK);
		
	}
	@RequestMapping(value = "/getSubDivisionBasedOnDivision/{divisionId}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<SubDivision>> findSubDivision(@PathVariable("divisionId") Division divisionId){
		List<SubDivision> subDivisionCodes= reportService.findSubDivision(divisionId);
			return new ResponseEntity<List<SubDivision>>(subDivisionCodes, HttpStatus.OK);		
	}
	@RequestMapping(value = "/getFacilityBasedOnSubDivision" ,method = RequestMethod.POST , headers= "accept=application/json")
	public ResponseEntity<List<Facility>> getFacilityBasedOnSubDivision(@RequestBody SubDivision  code){
		List<Facility> facilityNames = reportService.findFacilityNames(code.getCode());
		return new ResponseEntity<List<Facility>>(facilityNames,HttpStatus.OK);
		
	}
	@RequestMapping(value = "/getFacilityBasedOnSubDivision/{subDivision}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<Facility>> findFacility(@PathVariable("subDivision") String subDivision){
		List<Facility> facilityNames= reportService.findFacilityNames(subDivision);
		log.info("facilityNames"+facilityNames.size());
			return new ResponseEntity<List<Facility>>(facilityNames, HttpStatus.OK);		
	}
   @RequestMapping(value = "/getAllFunctionalLocationsTypes", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<FunctionalLocationTypes>> functionalLocationTypes(){
		List<FunctionalLocationTypes> functionalLocationTypes= reportService.findAllOrderByCodeAsc();
		return new ResponseEntity<List<FunctionalLocationTypes>>(functionalLocationTypes,HttpStatus.OK);	
		
	}
   @RequestMapping(value = "/getAllStipulationDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<Stipulations>> stipulationDetails(){
		List<Stipulations> stipulationDetails= reportService.findAllStipulationDetails();
		return new ResponseEntity<List<Stipulations>>(stipulationDetails,HttpStatus.OK);	
		
	}
   @RequestMapping(value = "/getAllUomDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
  	public ResponseEntity<List<Uom>> uomDetails(){
  		List<Uom> uomDetails= reportService.findAllUomDetails();
  		return new ResponseEntity<List<Uom>>(uomDetails,HttpStatus.OK);	
  		
  	}@RequestMapping(value = "/getAllSectionDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
  	public ResponseEntity<List<Section>> sectionDetails(){
  		List<Section> sectionDetails= reportService.findAllSectionDetails();
  		return new ResponseEntity<List<Section>>(sectionDetails,HttpStatus.OK);	
  		
  	}
  	@RequestMapping(value = "/getStatusCodeBasedOnStatusTypeId/{statusTypeId}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<StatusItem>> findAllStatusItem(@PathVariable("statusTypeId") String statusTypeId){
		List<StatusItem> allStatusItem= reportService.findAllStatusItem(statusTypeId);
		return new ResponseEntity<List<StatusItem>>(allStatusItem, HttpStatus.OK);		
	}
  	
  	@RequestMapping(value = "/getFacilitysBasedOnDepotType/{depotType}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<Facility>> getFacilitysBasedOnDepotType(@PathVariable("depotType") String depotType){
		List<Facility> facilityList= reportService.getFacilitysBasedOnDepotType(depotType);
			return new ResponseEntity<List<Facility>>(facilityList, HttpStatus.OK);		
	}
  	@RequestMapping(value = "/getAllCrsEigInspectionDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<CrsEigInspections>> findcrsEigInspection(){
		List<CrsEigInspections> crsEigInspection= reportService.findcrsEigInspection();
		return new ResponseEntity<List<CrsEigInspections>>(crsEigInspection,HttpStatus.OK);	
		
	}
  	@RequestMapping(value = "/getAllFailureAnalysisDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<FailureAnalysis>> findFailureAnalysis(){
		List<FailureAnalysis> failureAnalysis= reportService.findFailureAnalysis();
		return new ResponseEntity<List<FailureAnalysis>>(failureAnalysis,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getAllElectrificationTargetDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<ElectrificationTargets>> findElectrificationTargets(){
		List<ElectrificationTargets> electrificationTargets= reportService.findElectrificationTargets();
		return new ResponseEntity<List<ElectrificationTargets>>(electrificationTargets,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getAllPrecautionaryMeasureDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<PrecautionaryMeasuresMaster>> findPrecautionaryMeasures(){
		List<PrecautionaryMeasuresMaster> precautionaryMeasures= reportService.findPrecautionaryMeasures();
		return new ResponseEntity<List<PrecautionaryMeasuresMaster>>(precautionaryMeasures,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getAllTpcBoardDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<TpcBoard>> findTPCBoard(){
		List<TpcBoard> tpcBoard= reportService.findTPCBoard();
		return new ResponseEntity<List<TpcBoard>>(tpcBoard,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getAllProductMakeModelAssoc", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<ProductMakeModelAssociation>> findProductMakeModelAssoc(){
		List<ProductMakeModelAssociation> productMakeModelAssoc= reportService.findProductMakeModelAssoc();
		return new ResponseEntity<List<ProductMakeModelAssociation>>(productMakeModelAssoc,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getAllMajorSectionDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<MajorSections>> findMajorSection(){
		List<MajorSections> majorSections= reportService.findMajorSection();
		return new ResponseEntity<List<MajorSections>>(majorSections,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getAllMakeDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<Make>> findMake(){
		List<Make> makeDetails= reportService.findMakeDetails();
		return new ResponseEntity<List<Make>>(makeDetails,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getAllModelDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<Model>> findModel(){
		List<Model> modelDetails= reportService.findModelDetails();
		return new ResponseEntity<List<Model>>(modelDetails,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getAllAssetMasterData", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<AssetMasterData>> findAssetMasterDetails(){
		List<AssetMasterData> assetMasterData= reportService.findAssetMasterDetails();
		return new ResponseEntity<List<AssetMasterData>>(assetMasterData,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getAllGeographicState", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<GeographicState>> findGeographicState(){
		List<GeographicState> geographicState= reportService.findGeographicState();
		return new ResponseEntity<List<GeographicState>>(geographicState,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getAllElectricEnergySuppliers", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<ElectricEnergySuppliers>> findElectricEnergySuppliers(){
		List<ElectricEnergySuppliers> ees= reportService.findElectricEnergySuppliers();
		return new ResponseEntity<List<ElectricEnergySuppliers>>(ees,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getAllDivisionDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<Division>> findDivisionDetails(){
		List<Division> division= reportService.findByOrderByCodeAsc();
		return new ResponseEntity<List<Division>>(division,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getOheFacilityNames", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<Facility>> findByDepotTypeOrderByFacilityNameAsc(){
		List<Facility> oheDepotType= reportService.findByDepotTypeOrderByFacilityNameAsc();
		return new ResponseEntity<List<Facility>>(oheDepotType,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getOheAndPsiFacilityNames", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<Facility>> findByDepotType(String depotType){
		List<Facility> oheAndPsiDepotType= reportService.findByDepotType(depotType);
		return new ResponseEntity<List<Facility>>(oheAndPsiDepotType,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/findAllAssetTypes", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<ProductCategoryMember>> findByProductId(String productCategoryId){
		List<ProductCategoryMember> assets= reportService.findByProductId(productCategoryId);
		return new ResponseEntity<List<ProductCategoryMember>>(assets,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/getObservationCheckListBasedOnObservationCate/{observationCategory}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<ObservationsCheckList>> getObservationCheckListBasedOnObservationCate(@PathVariable("observationCategory") String observationCategory){
		List<ObservationsCheckList> obsCheckList= reportService.getObservationCheckListBasedOnObservationCate(observationCategory);
		log.info("obsCheckListSize"+obsCheckList.size());
		log.info("obsCheckList"+obsCheckList);

			return new ResponseEntity<List<ObservationsCheckList>>(obsCheckList, HttpStatus.OK);		
	}
	@RequestMapping(value = "/getActivityNameBasedOnActivityType/{activityType}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<MeasureOrActivityList>> getActivityNameBasedOnActivityType(@PathVariable("activityType") String activityType){
		List<MeasureOrActivityList> activityList= reportService.getActivityNameBasedOnActivityType(activityType);
			return new ResponseEntity<List<MeasureOrActivityList>>(activityList, HttpStatus.OK);		
	}
	@RequestMapping(value = "/getFacility/{facilityId}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<Facility> getFacility(@PathVariable("facilityId") String facilityId){
		log.info("Enter into getFacility function");
		Optional<Facility> facility = null;
				facilityId = facilityId.replace("\"", "");
				log.info("*** facility Id**"+facilityId);
				try {
					facility = reportService.findByFacilityId(facilityId);
					if(facility.isPresent()) {
						log.info("facility  Data = "+facility.get());
						return new ResponseEntity<Facility>(facility.get(), HttpStatus.OK);
					}
					else
						return new ResponseEntity<Facility>(facility.get(), HttpStatus.CONFLICT);
				} catch (Exception e) {
					log.error("Error >>  while find facility Details by facilityId, "+e.getMessage());
					return new ResponseEntity<Facility>(facility.get(), HttpStatus.INTERNAL_SERVER_ERROR);
				}
	}
  	
  	
}