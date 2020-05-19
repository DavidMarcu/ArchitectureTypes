import apiClient from './ApiClient.js';
import store from "../store/index";

apiClient.interceptors.request.use((config) => {
  config.headers = {'Authorization': store.getters.getAuthHeader}
  return config
})

const reviewService = {
    getReviewsForBook(isbn) {
      return apiClient.get(`books/reviews/${isbn}`)
    },
    addReviewForBook(data) {
      return apiClient.post(`books/reviews/review`, data)
    },
    updateReviewForBook(data) {
      return apiClient.put('books/reviews/review', data)
    },
    deleteReviewForBook(isbn) {
      return apiClient.delete(`books/reviews/review/${isbn}`)
    }
}

export default reviewService
