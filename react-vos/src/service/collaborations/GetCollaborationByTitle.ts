import { AxiosResponse } from 'axios';
import APIClient from "../APIClient";

export const GetCollaborationByTitle = async (title: string): Promise<any> => {
    try {
        const response: AxiosResponse = await APIClient.get(`collaborations/search?title=${title}`);
        return response.data;
    } catch (error) {
        console.error(`Error fetching collaboration data by title ${title}: `, error);
        throw error;
    }
};
