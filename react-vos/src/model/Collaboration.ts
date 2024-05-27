import {DetailsMembre} from "./DetailsMembre";

export interface Collaboration {
    idCollaboration:number;
    dateCreationCollaboration:string;
    titre:string;
    confidentielle:boolean;
    dateDepart:string;
    idProprietaire:string;
    participants:DetailsMembre[];
}