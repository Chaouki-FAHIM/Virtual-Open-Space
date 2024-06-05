import axios, {AxiosResponse} from 'axios';
import API_BASE_URL from "../../constant/URL";

export const GetAllMembers = async ():Promise<any> => {
    try {
        const response:AxiosResponse = await axios.get(`${API_BASE_URL['membre']}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching membre list data', error);
        throw error;
    }
};
