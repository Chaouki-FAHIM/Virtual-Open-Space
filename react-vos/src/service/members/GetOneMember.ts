import axios, {AxiosResponse} from 'axios';
import API_BASE_URL from "../../constant/URL";


export const GetOneMember = async (id:string):Promise<any> => {
    try {
        const response:AxiosResponse = await axios.get(`${API_BASE_URL['membre']}/${id}`);
        return response.data;
    } catch (error) {
        console.error(`Error fetching membre data who have ${id} : `, error);
        throw error;
    }
};
