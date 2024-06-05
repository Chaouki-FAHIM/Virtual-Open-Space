import axios, { AxiosResponse } from 'axios';
import API_BASE_URL from "../../constant/URL";

export const GetOneCollaboration = async (id: number): Promise<any> => {
    try {
        const response: AxiosResponse = await axios.get(`${API_BASE_URL['collaboration']}/${id}`);
        return response.data;
    } catch (error) {
        console.error(`Error fetching collaboration data for ID ${id}: `, error);
        throw error;
    }
};
