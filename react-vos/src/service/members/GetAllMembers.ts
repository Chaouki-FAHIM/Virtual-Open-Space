import axios, {AxiosResponse} from 'axios';

const API_BASE_URL:string = 'http://localhost:8081/membres';

export const GetAllMembers = async ():Promise<any> => {
    try {
        const response:AxiosResponse = await axios.get(`${API_BASE_URL}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching data', error);
        throw error;
    }
};
