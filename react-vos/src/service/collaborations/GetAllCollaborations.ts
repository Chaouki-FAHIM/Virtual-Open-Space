import axios, {AxiosResponse} from 'axios';

const COLLABORATIONS_API_BASE_URL:string = 'http://localhost:8080/collaborations';


export const GetAllCollaborations = async ():Promise<any> => {
    try {
        const response:AxiosResponse = await axios.get(`${COLLABORATIONS_API_BASE_URL}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching collaboration list data', error);
        throw error;
    }
};
