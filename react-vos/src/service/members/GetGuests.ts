import axios, {AxiosResponse} from 'axios';
import API_BASE_URL from "../../constant/URL";

export const GetGuests = async (idCollaboration:string):Promise<any> => {
    try {
        const response:AxiosResponse = await axios.get(`${API_BASE_URL['membre']}/${idCollaboration}/uninvited-members`);
        return response.data;
    } catch (error) {
        console.error('Error fetching membre list data', error);
        throw error;
    }
};