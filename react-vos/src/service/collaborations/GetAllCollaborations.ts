import axios, {AxiosResponse} from 'axios';
import API_BASE_URL from "../../constant/URL";


export const GetAllCollaborations = async ():Promise<any> => {
    try {
        const response:AxiosResponse = await axios.get(`${API_BASE_URL['collaboration']}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching collaboration list data', error);
        throw error;
    }
};
