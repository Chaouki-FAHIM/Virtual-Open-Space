import {AxiosResponse} from 'axios';
import APIClient from "../APIClient";


export const GetMemberByFullName = async (fullName:string):Promise<any> => {
    try {
        const response:AxiosResponse = await APIClient.get(`membres/${fullName}`);
        return response.data;
    } catch (error) {
        console.error(`Error fetching membre data by full name who have ${fullName} : `, error);
        throw error;
    }
};
