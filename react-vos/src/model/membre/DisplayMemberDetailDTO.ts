export interface DisplayMemberDetailDTO {
    idMembre: string;
    image:string;
    nomMembre: string;
    prenom: string;
    roleHabilation: string;
    statutCollaboration?: string;
    teams:TeamMembreDTO[]
}

interface TeamMembreDTO {
    idTeam:string;
    nomTeam:string;
    siege:string;
}