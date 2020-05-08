import apiClient from './ApiClient.js';
// import store from "../store";

const headerConfig = {headers: {'Authorization': localStorage.getItem("auth")}}

const bookService = {
    getAllBooks() {
        return apiClient.get('/', headerConfig)
    },
    insertBook(book) {
        return apiClient.post('/book', book, headerConfig)
    }
}

export default bookService
