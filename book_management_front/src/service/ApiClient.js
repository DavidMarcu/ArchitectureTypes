import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:9001',
  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  }
})

export default apiClient
