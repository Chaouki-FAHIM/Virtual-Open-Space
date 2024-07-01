// service/authentication/LoginService.js

import APIClient from "../APIClient";
import { AxiosResponse } from "axios";
import { LoginDTO } from "../../model/LoginDTO";

export const LoginService = async (loginRequestDTO: LoginDTO) => {
    try {
        const response: AxiosResponse = await APIClient.post('auth/login', loginRequestDTO);
        // Assuming the response contains access_token and refresh_token
        localStorage.setItem('access_token', response.data.access_token);
        localStorage.setItem('refresh_token', response.data.refresh_token);
        return response.data;
    } catch (error) {
        console.error('Error during authentication: ', error);
        throw error;
    }
};
