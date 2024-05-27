export interface DetailsMembre {
    idMembre: string;
    image:string;
    nomMembre: string;
    prenom: string;
    roleHabilation: string;
    statutCollaboration?: string;
    teams:TeamMembre[]
}

interface TeamMembre {
    idTeam:string;
    nomTeam:string;
    siege:string;
}