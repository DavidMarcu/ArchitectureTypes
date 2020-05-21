import apiClient from './ApiClient.js';
import store from "../store/index";

apiClient.interceptors.request.use((config) => {
    config.headers = {'Authorization': store.getters.getAuthHeader}
    return config
})

const bookService = {
    getBooksForUser(page, searchTerm = null) {
        const url = searchTerm === null ? `/books/user?page=${page}` : `/books/user?page=${page}&q=${searchTerm}`
        return apiClient.get(url)
    },
    getAllBooks(page, searchTerm = null) {
        const url = searchTerm === null ? `/books?page=${page}` : `/books?page=${page}&q=${searchTerm}`
        return apiClient.get(url)
    },
    getBookOwnership(isbn) {
      return apiClient.head(`/books/user/book/${isbn}`)
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
