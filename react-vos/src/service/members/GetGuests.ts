import {AxiosResponse} from 'axios';
import APIClient from "../APIClient";

export const GetGuests = async (idCollaboration:string):Promise<any> => {
    try {
        const response:AxiosResponse = await APIClient.get(`membres/${idCollaboration}/uninvited-members`);
        return response.data;
    } catch (error) {
        console.error('Error fetching membre list data', error);
        throw error;
    }
};