import {AxiosResponse} from 'axios';
import APIClient from "../APIClient";

export const GetAllMembers = async ():Promise<any> => {
    try {
        const response:AxiosResponse = await APIClient.get('/membres');
        return response.data;
    } catch (error) {
        console.error('Error fetching membre list data', error);
        throw error;
    }
};
