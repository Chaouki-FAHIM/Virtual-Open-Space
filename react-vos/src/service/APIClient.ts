import axios, {AxiosInstance} from 'axios';

const APIClient :AxiosInstance = axios.create({
    baseURL: 'http://localhost:8080',
    headers: {
        'Content-Type': 'application/json',
    },
});

APIClient.interceptors.response.use(
    response => response,
    error => {
        console.error('API call error:', error);
        return Promise.reject(error);
    }
);

export default APIClient;
