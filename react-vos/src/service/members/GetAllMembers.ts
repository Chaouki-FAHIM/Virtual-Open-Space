import axios, {AxiosResponse} from 'axios';
import MEMBRE_API_BASE_URL from '../../constant/URL';

export const GetAllMembers = async ():Promise<any> => {
    try {
        const response:AxiosResponse = await axios.get(`${MEMBRE_API_BASE_URL}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching membre list data', error);
        throw error;
    }
};
