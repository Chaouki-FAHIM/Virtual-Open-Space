import axios, { AxiosResponse } from 'axios';
import API_BASE_URL from "../../constant/URL";
import { CreateCollaborationDTO } from "../../model/collaboration/CreateCollaborationDTO";

export const CreateNewCollaboration = async (newCollaboration: CreateCollaborationDTO): Promise<any> => {
    newCollaboration.idProprietaire= '66512b04ee881f13a4e14d4d'
    try {
        const response: AxiosResponse = await axios.post(`${API_BASE_URL['collaboration']}`, newCollaboration);
        return response.data;
    } catch (error) {
        console.error(`Error creating a new collaboration: `, error);
        throw error;
    }
};
