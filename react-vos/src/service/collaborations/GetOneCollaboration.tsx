import axios, { AxiosResponse } from 'axios';

const COLLABORATIONS_API_BASE_URL: string = 'http://localhost:8080/collaborations';

export const GetOneCollaboration = async (id: number): Promise<any> => {
    try {
        const response: AxiosResponse = await axios.get(`${COLLABORATIONS_API_BASE_URL}/${id}`);
        return response.data;
    } catch (error) {
        console.error(`Error fetching collaboration data for ID ${id}: `, error);
        throw error;
    }
};
