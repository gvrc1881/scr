export interface TractionEnergyTariffModel {
	id:number;
	supplier: string;
	year: string;
    rate: number;
    specification: string;
    condition: string;
    fromDate: Date;
    thruDate: Date;
}