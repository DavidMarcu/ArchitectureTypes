<template>
  <div>
    <v-container>
      <div class="row">
        <div class="col-md-12">
          <v-row dense>
            <v-col md="4" class="pl-6 pt-6">
              <small>Showing {{leftNumber}}-{{rightNumber}} of {{books.count}} books</small>
            </v-col>
            <v-col md="4">
              <v-btn href="/books" color="success">
                <v-icon>mdi-book-plus</v-icon>
                Add new book
              </v-btn>
            </v-col>
            <v-col md="4">
              <v-select class="pa-0" v-model="select" :items="options" style="margin-bottom: -20px;" outlined dense></v-select>
            </v-col>
          </v-row>

          <v-divider></v-divider>

          <div class="row text-center">
            <div class="col-xl-2 col-md-3 col-sm-6 col-xs-12" :key="book.isbn" v-for="book in books.books">
              <v-hover v-slot:default="{ hover }">
                <v-card
                  class="mx-auto"
                  color="grey lighten-4"
                  max-width="600"
                >
                  <v-img
                    class="white--text align-end"
                    :aspect-ratio="16/9"
                    height="400px"
                    :src="imageSource(book)"
                  >
                    <v-expand-transition>
                      <div
                        v-if="hover"
                        class="d-flex transition-fast-in-fast-out white darken-2 v-card--reveal display-3 white--text"
                        style="height: 100%;"
                      >
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
          <div class="text-center mt-12">
            <v-pagination
              v-model="page"
              @next="fetchBooks"
              @previous="fetchBooks"
              @input="fetchBooks"
              :length="pageLength"
            ></v-pagination>
          </div>
        </div>
      </div>
    </v-container>
  </div>
</template>
<style>
  .v-card--reveal {
    align-items: center;
    bottom: 0;
    justify-content: center;
    opacity: .8;
    position: absolute;
    width: 100%;
  }
</style>
<script>
    export default {
      created() {
          this.$store.dispatch("fetchBooks", this.page)
      },
      data: () => ({
            select: 'Title',
            options: [
                'Rating',
                'Title',
                'Authors'
            ],
            page: 1
        }),
      methods: {
        imageSource(book) {
          return `data:image/${book.coverImageType};base64,${book.coverImage}`
        },
        fetchBooks() {
          this.$store.dispatch("fetchBooks", this.page)
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
          return (this.page - 1) * this.books.countPerPage + 1
        },
        rightNumber() {
          return this.page === this.pageLength ? this.books.count : this.page * this.books.countPerPage
        }
      }
    }
</script>
