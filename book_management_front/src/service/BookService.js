import apiClient from './ApiClient.js';
import store from "../store/index";

apiClient.interceptors.request.use((config) => {
    config.headers = {'Authorization': store.getters.getAuthHeader}
    return config
})

const bookService = {
    getAllBooks() {
        return apiClient.get(`/books/user/${store.getters.getUserId}`)
    },
    insertBook(book) {
        return apiClient.post('/books/book', book)
    }
}

export default bookService
