export interface StandardPhasesModel{
    id: number;    
    name: String;
    description: String;
    sequence:number;
    dependencyToStart:String;
    typeOfWork:String;
    weightage: number;
}