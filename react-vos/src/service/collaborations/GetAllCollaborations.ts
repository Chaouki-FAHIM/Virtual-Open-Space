import {AxiosResponse} from 'axios';
import APIClient from "../APIClient";


export const GetAllCollaborations = async ():Promise<any> => {
    try {
        const response:AxiosResponse = await APIClient.get('/collaborations');
        return response.data;
    } catch (error) {
        console.error('Error fetching collaboration list data', error);
        throw error;
    }
};
