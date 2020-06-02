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
    books: {
      books: [],
      count: 0,
      countPerPage: 0
    },
    pageNumberUser: 1,
    allBooks: {
      books: [],
      count: 0,
      countPerPage: 0
    },
    pageNumberAll: 1,
    userAuthorization: null,
    notificationObject: null
  },
  mutations: {
    SET_BOOKS(state, response) {
      state.books.books = response.books
      state.books.count = response.totalBooks
      state.books.countPerPage = response.booksPerPage
    },
    SET_PAGE(state, page) {
      state.pageNumberUser = page
    },
    SET_ALL_BOOKS(state, response) {
      state.allBooks.books = response.books
      state.allBooks.count = response.totalBooks
      state.allBooks.countPerPage = response.booksPerPage
    },
    SET_ALL_PAGE(state, page) {
      state.pageNumberAll = page
    },
    SET_AUTHORIZATION(state, authentication) {
      state.userAuthorization = authentication
    },
    SET_NOTIFICATION(state, notification) {
      state.notificationObject = notification
    }
  },
  actions: {
    fetchBooks(context, payloadObject) {
      bookService.getBooksForUser(payloadObject.page, payloadObject.searchTerm)
          .then(response => {
            context.commit('SET_BOOKS', response.data)
            context.commit('SET_PAGE', response.data.lastPage)
          })
          .catch(error => {
            console.error("Error on get request: " + error)
          })
    },
    fetchAllBooks(context, payloadObject) {
      bookService.getAllBooks(payloadObject.page, payloadObject.searchTerm)
          .then(response => {
            context.commit('SET_ALL_BOOKS', response.data)
            context.commit('SET_ALL_PAGE', response.data.lastPage)
          })
          .catch(error => console.error(error))
    },
    loginUser(context, user) {
      return new Promise((resolve, reject) =>
        userService.login(user)
            .then(response => {context.commit('SET_AUTHORIZATION', response.data); resolve(response)})
            .catch(error => { reject(error.response) })
      )},
    emitNotification(context, notification) {
      context.commit('SET_NOTIFICATION', notification)
    }
  },
  getters: {
    isLoggedIn(state) {
      return state.userAuthorization !== null
    },
    getAuthHeader(state) {
      const authorization = state.userAuthorization
      return authorization !== null ? authorization.authorizationHeader : null
    },
    ownsBook: state => isbn => {
      const bookIsbns = state.books.books.map(book => book.isbn)
      return bookIsbns.includes(isbn)
    },
    getBooksObject(state) {
      return state.books
    },
    getAllBooksObject(state) {
      return state.allBooks
    },
    getNotification(state) {
      return state.notificationObject
    }
  },
  modules: {
  }
})

export default store
