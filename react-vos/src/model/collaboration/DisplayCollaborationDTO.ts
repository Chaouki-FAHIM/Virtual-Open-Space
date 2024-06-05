import {DisplayMemberDetailDTO} from "../membre/DisplayMemberDetailDTO";

export interface DisplayCollaborationDTO {
    idCollaboration:number;
    dateCreationCollaboration:string;
    titre:string;
    confidentielle:boolean;
    dateDepart:string;
    idProprietaire:string;
    participants:DisplayMemberDetailDTO[];
}