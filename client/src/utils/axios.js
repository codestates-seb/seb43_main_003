import axios from 'axios';

const BASE_URL = import.meta.env.VITE_BASE_URL;
const SERVER_URL = import.meta.env.VITE_SERVER_URL;
const NROGK = import.meta.env.VITE_NGROK;

const config = {
  baseURL: SERVER_URL,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
    'ngrok-skip-browser-warning': '69420',
  },
};

export default axios.create(config);
