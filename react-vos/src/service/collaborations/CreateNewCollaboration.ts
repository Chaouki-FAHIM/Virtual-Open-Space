import axios, { AxiosResponse } from 'axios';
import API_BASE_URL from "../../constant/URL";
import { CreateCollaborationDTO } from "../../model/collaboration/CreateCollaborationDTO";

export const CreateNewCollaboration = async (newCollaboration: CreateCollaborationDTO): Promise<any> => {
    try {
        const response: AxiosResponse = await axios.post(`${API_BASE_URL['collaboration']}`, newCollaboration);
        return response.data;
    } catch (error) {
        console.error(`Error creating a new collaboration: `, error);
        throw error;
    }
};
