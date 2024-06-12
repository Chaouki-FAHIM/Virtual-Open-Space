import axios from 'axios';
import {CreateCollaborationDTO} from "../../model/collaboration/CreateCollaborationDTO";

export const CreateNewCollaboration = async (newCollaboration:CreateCollaborationDTO) => {
    try {
        const response = await axios.post('http://localhost:8080/collaborations', newCollaboration, {
            headers: {
                'Content-Type': 'application/json',
            },
            withCredentials: true,
        });
        return response.data;
    } catch (error) {
        console.error('Error creating a new collaboration: ', error);
        throw error;
    }
};
