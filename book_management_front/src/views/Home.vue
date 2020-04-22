<template>
  <div>
    <b-container fluid>
      <b-row cols="5">
        <b-col v-for="book in books" v-bind:key="book.isbn">
          <BookElement
            v-bind:img-src="book.coverImagePath"
            v-bind:book-title="book.title"
            v-bind:book-description="book.description"
            v-bind:authors="book.authors"
          />
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import BookElement from "@/components/BookElement.vue";
export default {
  components: {
    BookElement
  },
  created() {
    this.$store.dispatch("fetchBooks");
  },
  computed: {
    books() {
      return this.$store.state.books;
    }
  }
};
</script>

<style scoped>
</style>