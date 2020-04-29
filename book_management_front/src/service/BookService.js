import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:9001/books',
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    }
})

const bookService = {
    getAllBooks() {
        return apiClient.get('/')
    }
}

export default bookService
