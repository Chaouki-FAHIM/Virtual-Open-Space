import {AxiosResponse} from 'axios';
import APIClient from "../APIClient";


export const GetOneMember = async (id:string):Promise<any> => {
    try {
        const response:AxiosResponse = await APIClient.get(`membres/${id}`);
        return response.data;
    } catch (error) {
        console.error(`Error fetching membre data who have ${id} : `, error);
        throw error;
    }
};
