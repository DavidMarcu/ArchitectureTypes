import Vue from 'vue'
import Vuex from 'vuex'
import bookService from '@/service/BookService.js';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    books: []
  },
  mutations: {
    SET_BOOKS(state, books) {
      state.books = books
    }
  },
  actions: {
    fetchBooks(context) {
      bookService.getAllBooks()
          .then(response => {
            context.commit('SET_BOOKS', response.data)
          })
          .catch(error => {
            console.log("Error on get request: " + error)
          })
    }
  },
  modules: {
  }
})
