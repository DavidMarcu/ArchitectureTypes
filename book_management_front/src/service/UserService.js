import apiClient from './ApiClient.js';

const userService = {
    login(user) {
      return apiClient.post('/login', user)
    },
    signup(user) {
      return apiClient.post('/signup', user)
    }
}

export default userService
