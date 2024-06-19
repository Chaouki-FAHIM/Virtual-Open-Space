import {CreateCollaborationDTO} from "../../model/collaboration/CreateCollaborationDTO";
import APIClient from "../APIClient";
import {AxiosResponse} from "axios";

export const CreateNewCollaboration = async (newCollaboration:CreateCollaborationDTO) => {
    try {
        const response:AxiosResponse = await APIClient.post('collaborations', newCollaboration);
        return response.data;
    } catch (error) {
        console.error('Error creating a new collaboration: ', error);
        throw error;
    }
};
