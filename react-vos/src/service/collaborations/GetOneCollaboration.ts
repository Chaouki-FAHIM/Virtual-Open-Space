import { AxiosResponse } from 'axios';
import APIClient from "../APIClient";

export const GetOneCollaboration = async (id: number): Promise<any> => {
    try {
        const response: AxiosResponse = await APIClient.get(`/${id}`);
        return response.data;
    } catch (error) {
        console.error(`Error fetching collaboration data for ID ${id}: `, error);
        throw error;
    }
};
