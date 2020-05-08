import apiClient from './ApiClient.js';

const userService = {
    login(user) {
      return apiClient.post('/login', user)
    }
}

export default userService
