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
              <NewBook></NewBook>
            </v-col>
            <v-col md="4">
              <v-form @submit.prevent="searchBooks">
                <v-text-field
                      v-model="searchTerm"
                      flat
                      hide-details
                      prepend-inner-icon="mdi-magnify"
                      label="Search"
                      class="hidden-sm-and-down pl-10 ml-4"/>
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
                    @next="fetchAllBooks"
                    @previous="fetchAllBooks"
                    @input="fetchAllBooks"
                    :total-visible="5"
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
  import NewBook from "@/components/NewBook.vue";

  export default {
    components: {
      NewBook
    },
    created() {
      const payloadObject = {
        page: this.page,
        searchTerm: null
      }
      this.$store.dispatch("fetchAllBooks", payloadObject)
    },
    data: () => ({
      searchTerm: "",
      page:1
    }),
    methods: {
      imageSource(book) {
        return `data:image/${book.coverImageType};base64,${book.coverImage}`
      },
      fetchAllBooks() {
        const payloadObject = {
          page: this.page,
          searchTerm: null
        }
        this.$store.dispatch("fetchAllBooks", payloadObject)
      },
      searchBooks() {
        const payloadObject = {
          page: this.page,
          searchTerm: this.searchTerm
        }
        this.$store.dispatch("fetchAllBooks", payloadObject)
      }
    },
    computed: {
      books() {
        return this.$store.getters.getAllBooksObject;
      },
      pageLength() {
        return this.$store.state.pageNumberAll
      },
      leftNumber() {
        return this.books.count === 0 ? 0 : (this.page - 1) * this.books.countPerPage + 1
      },
      rightNumber() {
        if(this.books.count === 0) return 0
        else return this.page === this.pageLength ? this.books.count : this.page * this.books.countPerPage
      }
    }
  }
</script>
