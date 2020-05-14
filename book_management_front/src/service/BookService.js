import apiClient from './ApiClient.js';
import store from "../store/index";

apiClient.interceptors.request.use((config) => {
    config.headers = {'Authorization': store.getters.getAuthHeader}
    return config
})

const bookService = {
    getBooksForUser() {
        return apiClient.get('/books/user')
    },
    getAllBooks() {
        return apiClient.get('/books')
    },
    insertBook(book) {
        return apiClient.post('/books', book)
    },
    getBookByIsbn(isbn) {
        return apiClient.get(`books/book/${isbn}`)
    },
    removeBookForUser(isbn) {
        return apiClient.delete(`books/book/${isbn}`)
    },
    addBookForUser(data) {
        return apiClient.post('books/book/', data)
    }
}

export default bookService
