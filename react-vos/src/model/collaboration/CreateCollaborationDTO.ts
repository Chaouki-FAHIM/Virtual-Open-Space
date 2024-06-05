
export interface CreateCollaborationDTO {
    titre:string;
    confidentielle:boolean;
    dateDepart:string;
    idProprietaire:string;
    idParticipants: string[];
}