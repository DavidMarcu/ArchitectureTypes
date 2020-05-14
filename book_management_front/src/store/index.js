import Vue from 'vue'
import Vuex from 'vuex'
import bookService from '@/service/BookService.js';
import userService from "@/service/UserService";
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

const store = new Vuex.Store({
  plugins: [createPersistedState({
    storage: window.sessionStorage
  })],
  state: {
    books: [],
    allBooks: [],
    userAuthorization: null
  },
  mutations: {
    SET_BOOKS(state, books) {
      state.books = books
    },
    SET_ALL_BOOKS(state, books) {
      state.allBooks = books
    },
    SET_AUTHORIZATION(state, authentication) {
      state.userAuthorization = authentication
    },
  },
  actions: {
    fetchBooks(context) {
      bookService.getBooksForUser()
          .then(response => {
            context.commit('SET_BOOKS', response.data)
          })
          .catch(error => {
            console.log("Error on get request: " + error)
          })
    },
    fetchAllBooks(context) {
      bookService.getAllBooks()
          .then(response => context.commit('SET_ALL_BOOKS', response.data))
          .catch(error => console.log(error))
    },
    insertBook(context, book) {
      bookService.insertBook(book)
          .then(() => context.dispatch('fetchBooks'))
          .catch(error => console.log("Error on post request: " + error))
    },
    loginUser(context, user) {
      return new Promise((resolve, reject) =>
        userService.login(user)
            .then(response => {context.commit('SET_AUTHORIZATION', response.data); resolve(response)})
            .catch(error => { reject(error.response) })
      )}
  },
  getters: {
    isLoggedIn(state) {
      return state.userAuthorization !== null
    },
    getAuthHeader(state) {
      const authorization = state.userAuthorization
      return authorization !== null ? authorization.authorizationHeader : null
    }
  },
  modules: {
  }
})

export default store
