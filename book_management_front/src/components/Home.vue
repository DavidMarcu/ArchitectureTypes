<template>
  <div ref="container" class="h-100">
    <v-container class="h-100 comp">
      <div class="content row">
        <div class="col-md-12">
          <v-row dense>
            <v-col cols="12" md="4" class="pl-6 pt-6">
              <small>Showing {{leftNumber}}-{{rightNumber}} of {{books.count}} books</small>
            </v-col>
            <v-col cols="12" md="4">
              <v-btn href="/books" color="success">
                <v-icon>mdi-book-plus</v-icon>
                Add new book
              </v-btn>
            </v-col>
            <v-col cols="12" md="4">
              <v-form @submit.prevent="searchBooks">
                <v-text-field
                      v-model="searchTerm"
                      flat
                      hide-details
                      prepend-inner-icon="mdi-magnify"
                      label="Search"/>
              </v-form>
            </v-col>
          </v-row>

          <v-divider></v-divider>

          <div class="row text-center">
            <div class="col-xl-2 col-md-3 col-sm-6 col-xs-12" :key="book.isbn" v-for="book in books.books">
              <v-hover v-slot:default="{ hover }">
                <v-card
                  class="mx-auto"
                  color="grey lighten-4"
                  max-width="600">
                  <v-img
                    class="white--text align-end"
                    :aspect-ratio="16/9"
                    height="400px"
                    :src="imageSource(book)">
                    <v-expand-transition>
                      <div
                        v-if="hover"
                        class="d-flex transition-fast-in-fast-out white darken-2 v-card--reveal display-3 white--text"
                        style="height: 100%;">
                        <v-btn v-if="hover" :href="'/book/' + book.isbn" outlined>VIEW</v-btn>
                      </div>

                    </v-expand-transition>
                  </v-img>
                  <v-card-text class="text--primary">
                    <div><a :href="'/book/' + book.isbn" style="text-decoration: none"><h3>{{book.title}}</h3></a></div>
                    <div>{{book.authors.toString()}}</div>
                  </v-card-text>
                </v-card>
              </v-hover>
            </div>
          </div>
        </div>
      </div>
      <div class="footer text-center">
        <v-pagination
                v-model="page"
                @next="fetchBooks"
                @previous="fetchBooks"
                @input="fetchBooks"
                :length="pageLength"></v-pagination>
      </div>
    </v-container>
  </div>
</template>
<style scoped>
  .v-card--reveal {
    align-items: center;
    bottom: 0;
    justify-content: center;
    opacity: .8;
    position: absolute;
    width: 100%;
  }
  .h-100 {
    height: 100%;
  }
  .comp {
    display: flex;
    flex-direction: column;
  }
  .content {
    flex: 1 0 auto;
  }
  .footer {
    flex-shrink: 0;
  }
</style>
<script>
    import addNotification from "../helpers/NotificationHelper";
    import notifications from "../helpers/NotificationProperties";

    export default {
      created() {
        const payloadObject = {
          page: this.page,
          searchTerm: null
        }
        this.$store.dispatch("fetchBooks", payloadObject)
      },
      mounted() {
        const notification = this.$store.getters.getNotification
        switch (notification) {
          case notifications.SUCCESSFUL_BOOK_TO_USER:
            this.$refs.container.appendChild(addNotification(notifications.SUCCESSFUL_BOOK_TO_USER).$el)
            break
          case notifications.SUCCESSFUL_BOOK_REMOVE_FROM_USER:
            this.$refs.container.appendChild(addNotification(notifications.SUCCESSFUL_BOOK_REMOVE_FROM_USER).$el)
            break
        }
        this.$store.dispatch("emitNotification", null)
      },
      data: () => ({
            searchTerm: "",
            page: 1
        }),
      methods: {
        imageSource(book) {
          return `data:image/${book.coverImageType};base64,${book.coverImage}`
        },
        fetchBooks() {
          const payloadObject = {
            page: this.page,
            searchTerm: null
          }
          this.$store.dispatch("fetchBooks", payloadObject)
        },
        searchBooks() {
          const payloadObject = {
            page: this.page,
            searchTerm: this.searchTerm
          }
          this.$store.dispatch("fetchBooks", payloadObject)
        }
      },
      computed: {
        books() {
          return this.$store.getters.getBooksObject;
        },
        pageLength() {
          return this.$store.state.pageNumberUser
        },
        leftNumber() {
          return this.books.count === 0 ? 0 : (this.page - 1) * this.books.countPerPage + 1
        },
        rightNumber() {
          if(this.books.count === 0) return 0
          else return this.page === this.pageLength ? this.books.count : this.page * this.books.countPerPage
        },
        notification() {
          return this.$store.getters.getNotification
        }
      }
    }
</script>
