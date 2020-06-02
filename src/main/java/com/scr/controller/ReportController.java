package com.scr.controller;


import java.util.List;
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
	
	
	@RequestMapping(value = "/reportNames/{reportType}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<ReportRepository>> findAllDailyReports(@PathVariable("reportType") String reportType){
		List<ReportRepository> reportNamesGroup= reportService.findAllReportNames(reportType);
			return new ResponseEntity<List<ReportRepository>>(reportNamesGroup, HttpStatus.OK);		
	}
	@RequestMapping(value = "/allAssetTypeReports/{assetType}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<ProductCategoryMember>> findAllAssetTypes(@PathVariable("assetType") String assetType){
		List<ProductCategoryMember> allAssetTypes= reportService.findAllAssetTypes(assetType);
		log.info("allAssetTypes"+allAssetTypes);
			return new ResponseEntity<List<ProductCategoryMember>>(allAssetTypes, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/reportParameterNames", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<ReportParameter>> findall(){
		List<ReportParameter> reportPNames= reportService.findall();
			return new ResponseEntity<List<ReportParameter>>(reportPNames, HttpStatus.OK);	
		
	}
	
    @RequestMapping(value = "/facilityNames", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<Facility>> findAll(){
		List<Facility> facilityNames= reportService.findAllOrderByFacilityNameAsc();
		return new ResponseEntity<List<Facility>>(facilityNames, HttpStatus.OK);	
		
	}
    @RequestMapping(value = "/zoneList", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<Zone>> findAllZoneCodes(){
		List<Zone> zone= reportService.findAllOrderByCodeAsc1();
		return new ResponseEntity<List<Zone>>(zone, HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/failuresTable", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<Failure>> findAllFailures(){
		List<Failure> failuresTable= reportService.findAllFailures();
		return new ResponseEntity<List<Failure>>(failuresTable, HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/powerBlocks", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<PowerBlock>> findAllPowerBlocks(){
		List<PowerBlock> powerBlocks= reportService.findAllPowerBlocks();
		return new ResponseEntity<List<PowerBlock>>(powerBlocks, HttpStatus.OK);	
		
	}
	
	
	@RequestMapping(value = "/pbSwitchControl", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<PbSwitchControl>> findAllPBSwitch(){
		List<PbSwitchControl> pbSwitchControl= reportService.findAllPBSwitch();
		return new ResponseEntity<List<PbSwitchControl>>(pbSwitchControl,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/elementarySectionsByFacilityId", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<ElementarySection>> findAllElementarySection(@PathVariable("facilityId") String facilityId){
		List<ElementarySection> elementarySections= reportService.findAllElementarySection(facilityId);
		return new ResponseEntity<List<ElementarySection>>(elementarySections,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/observationCategories", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<ObservationCategory>> findDepartments(){
		List<ObservationCategory> department= reportService.findDepartments();
		return new ResponseEntity<List<ObservationCategory>>(department,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/observationCheckList", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<ObservationsCheckList>> findObservationCheckList(){
		List<ObservationsCheckList> observationCategory= reportService.findObservationCheckList();
		return new ResponseEntity<List<ObservationsCheckList>>(observationCategory,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/inspectionTypeData", method = RequestMethod.GET ,headers = "accept=application/json")	
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
	@RequestMapping(value = "/getAssetIdBasedOnScheduleCodesAndAssetTypes" ,method = RequestMethod.POST , headers= "accept=application/json")
	public ResponseEntity<List<AssetsScheduleHistory>> getAssetIdBasedOnScheduleCodesAndAssetTypes(@RequestBody AssetsScheduleHistory assetsScheHistObj){
		List<AssetsScheduleHistory> scheduleCodes = reportService.findAssetIdScheduleCodes( assetsScheHistObj.getAssetType(), assetsScheHistObj.getScheduleCode());
		return new ResponseEntity<List<AssetsScheduleHistory>>(scheduleCodes,HttpStatus.OK);
		
	}
	@RequestMapping(value = "/getDivisionBasedOnZone" ,method = RequestMethod.POST , headers= "accept=application/json")
	public ResponseEntity<List<Division>> getDivisionBasedOnZone(@RequestBody Zone code){
		List<Division> DivisionCodes = reportService.findDivision( code);
		return new ResponseEntity<List<Division>>(DivisionCodes,HttpStatus.OK);
		
	}
	@RequestMapping(value = "/getSubDivisionBasedOnDivision" ,method = RequestMethod.POST , headers= "accept=application/json")
	public ResponseEntity<List<SubDivision>> getSubDivisionBasedOnDivision(@RequestBody Division code){
		List<SubDivision> subDivisionCodes = reportService.findSubDivision( code);
		return new ResponseEntity<List<SubDivision>>(subDivisionCodes,HttpStatus.OK);
		
	}
	@RequestMapping(value = "/getFacilityBasedOnSubDivision" ,method = RequestMethod.POST , headers= "accept=application/json")
	public ResponseEntity<List<Facility>> getFacilityBasedOnSubDivision(@RequestBody SubDivision  code){
		List<Facility> facilityNames = reportService.findFacilityNames(code.getCode());
		return new ResponseEntity<List<Facility>>(facilityNames,HttpStatus.OK);
		
	}
   @RequestMapping(value = "/functionalLocationsTypes", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<FunctionalLocationTypes>> functionalLocationTypes(){
		List<FunctionalLocationTypes> functionalLocationTypes= reportService.findAllOrderByCodeAsc();
		return new ResponseEntity<List<FunctionalLocationTypes>>(functionalLocationTypes,HttpStatus.OK);	
		
	}
   @RequestMapping(value = "/stipulationDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<Stipulations>> stipulationDetails(){
		List<Stipulations> stipulationDetails= reportService.findAllStipulationDetails();
		return new ResponseEntity<List<Stipulations>>(stipulationDetails,HttpStatus.OK);	
		
	}
   @RequestMapping(value = "/uomDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
  	public ResponseEntity<List<Uom>> uomDetails(){
  		List<Uom> uomDetails= reportService.findAllUomDetails();
  		return new ResponseEntity<List<Uom>>(uomDetails,HttpStatus.OK);	
  		
  	}@RequestMapping(value = "/sectionDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
  	public ResponseEntity<List<Section>> sectionDetails(){
  		List<Section> sectionDetails= reportService.findAllSectionDetails();
  		return new ResponseEntity<List<Section>>(sectionDetails,HttpStatus.OK);	
  		
  	}
  	@RequestMapping(value = "/statusItemDetails/{statusTypeId}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<StatusItem>> findAllStatusItem(@PathVariable("statusTypeId") String statusTypeId){
		List<StatusItem> allStatusItem= reportService.findAllStatusItem(statusTypeId);
		return new ResponseEntity<List<StatusItem>>(allStatusItem, HttpStatus.OK);		
	}
  	
  	@RequestMapping(value = "/getFacilitysBasedOnDepotType/{depotType}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<Facility>> getFacilitysBasedOnDepotType(@PathVariable("depotType") String depotType){
		List<Facility> facilityList= reportService.getFacilitysBasedOnDepotType(depotType);
			return new ResponseEntity<List<Facility>>(facilityList, HttpStatus.OK);		
	}
  	@RequestMapping(value = "/crsEigInspectionDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<CrsEigInspections>> findcrsEigInspection(){
		List<CrsEigInspections> crsEigInspection= reportService.findcrsEigInspection();
		return new ResponseEntity<List<CrsEigInspections>>(crsEigInspection,HttpStatus.OK);	
		
	}
  	@RequestMapping(value = "/failureAnalysisDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<FailureAnalysis>> findFailureAnalysis(){
		List<FailureAnalysis> failureAnalysis= reportService.findFailureAnalysis();
		return new ResponseEntity<List<FailureAnalysis>>(failureAnalysis,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/electrificationTargetDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<ElectrificationTargets>> findElectrificationTargets(){
		List<ElectrificationTargets> electrificationTargets= reportService.findElectrificationTargets();
		return new ResponseEntity<List<ElectrificationTargets>>(electrificationTargets,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/precautionaryMeasureDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<PrecautionaryMeasuresMaster>> findPrecautionaryMeasures(){
		List<PrecautionaryMeasuresMaster> precautionaryMeasures= reportService.findPrecautionaryMeasures();
		return new ResponseEntity<List<PrecautionaryMeasuresMaster>>(precautionaryMeasures,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/tpcBoardDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<TpcBoard>> findTPCBoard(){
		List<TpcBoard> tpcBoard= reportService.findTPCBoard();
		return new ResponseEntity<List<TpcBoard>>(tpcBoard,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/productMakeModelAssoc", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<ProductMakeModelAssociation>> findProductMakeModelAssoc(){
		List<ProductMakeModelAssociation> productMakeModelAssoc= reportService.findProductMakeModelAssoc();
		return new ResponseEntity<List<ProductMakeModelAssociation>>(productMakeModelAssoc,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/majorSectionDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<MajorSections>> findMajorSection(){
		List<MajorSections> majorSections= reportService.findMajorSection();
		return new ResponseEntity<List<MajorSections>>(majorSections,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/makeDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<Make>> findMake(){
		List<Make> makeDetails= reportService.findMakeDetails();
		return new ResponseEntity<List<Make>>(makeDetails,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/modelDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<Model>> findModel(){
		List<Model> modelDetails= reportService.findModelDetails();
		return new ResponseEntity<List<Model>>(modelDetails,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/assetTypeBasedonAssetIdAndFacilityId/{assetType}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<AssetMasterData>> findAssetIdAndFacilityId(@PathVariable("assetType") String assetType){
		List<AssetMasterData> allAssetTypes= reportService.findAssetIdAndFacilityId(assetType, assetType);
			return new ResponseEntity<List<AssetMasterData>>(allAssetTypes, HttpStatus.OK);		
	}
	@RequestMapping(value = "/assetIdBasedonAssetTypeAndFacilityId/{assetId}",method = RequestMethod.GET  , headers="accept=application/json" )
	public ResponseEntity<List<AssetMasterData>> findAssetTypeAndFacilityId(@PathVariable("assetId") String assetId){
		List<AssetMasterData> allAssetTypes= reportService.findAssetTypeAndFacilityId(assetId, assetId);
			return new ResponseEntity<List<AssetMasterData>>(allAssetTypes, HttpStatus.OK);		
	}
	@RequestMapping(value = "/assetMasterData", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<AssetMasterData>> findAssetMasterDetails(){
		List<AssetMasterData> assetMasterData= reportService.findAssetMasterDetails();
		return new ResponseEntity<List<AssetMasterData>>(assetMasterData,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/geographicState", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<GeographicState>> findGeographicState(){
		List<GeographicState> geographicState= reportService.findGeographicState();
		return new ResponseEntity<List<GeographicState>>(geographicState,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/electricEnergySuppliers", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<ElectricEnergySuppliers>> findElectricEnergySuppliers(){
		List<ElectricEnergySuppliers> ees= reportService.findElectricEnergySuppliers();
		return new ResponseEntity<List<ElectricEnergySuppliers>>(ees,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/divisionDetails", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<Division>> findDivisionDetails(){
		List<Division> division= reportService.findByOrderByCodeAsc();
		return new ResponseEntity<List<Division>>(division,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/depotTypeForOhe", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<Facility>> findByDepotTypeOrderByFacilityNameAsc(){
		List<Facility> oheDepotType= reportService.findByDepotTypeOrderByFacilityNameAsc();
		return new ResponseEntity<List<Facility>>(oheDepotType,HttpStatus.OK);	
		
	}
	@RequestMapping(value = "/depotTypeOheAndPsi", method = RequestMethod.GET ,headers = "accept=application/json")	
	public ResponseEntity<List<Facility>> findByDepotType(String depotType){
		List<Facility> oheAndPsiDepotType= reportService.findByDepotType(depotType);
		return new ResponseEntity<List<Facility>>(oheAndPsiDepotType,HttpStatus.OK);	
		
	}
  	
}