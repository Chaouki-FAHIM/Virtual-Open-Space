import axios, {AxiosResponse} from 'axios';
import MEMBRE_API_BASE_URL from "../../constant/URL";


export const GetOneMember = async (id:string):Promise<any> => {
    try {
        const response:AxiosResponse = await axios.get(`${MEMBRE_API_BASE_URL}/${id}`);
        return response.data;
    } catch (error) {
        console.error(`Error fetching membre data who have ${id} : `, error);
        throw error;
    }
};
