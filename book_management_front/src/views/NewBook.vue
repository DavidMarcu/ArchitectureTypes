<template>
  <div>
    <b-form v-on:submit.prevent="onSubmit">
      <b-form-group 
        id="isbn-group"
        label="ISBN:"
        label-for="isbn"
        description="Book unique identifier, must be 10 or 13 characters long">
        <b-form-input 
          id="isbn"
          v-bind:state="isbnValidation"
          v-model="book.isbn"
          type="text"
          required
          placeholder="Enter ISBN...">
        </b-form-input>
      </b-form-group>
     
      <b-form-group 
        id="title-group"
        label="Title:"
        label-for="title">
        <b-form-input 
          id="title"
          v-bind:state="book.title.length > 0"
          v-model="book.title"
          type="text"
          required
          placeholder="Enter title...">
        </b-form-input>
      </b-form-group>
      
      <b-form-group 
        id="authors-group"
        label="Authors:"
        label-for="authors">
        <b-input-group id="authors" v-for="(author, index) in book.authors" v-bind:key="index">
            <b-form-input
              class="mt-1"
              v-model="book.authors[index]"
              type="text"
              v-bind:state="author.length > 0"
              required
              v-bind:placeholder="authorPlaceholder(index)">
            </b-form-input>
            <b-input-group-append>
              <b-icon class="mx-2" icon="trash" variant="danger" v-on:click="removeAuthor(author)"></b-icon>
              <b-icon class="mx-2" icon="plus-circle-fill" variant="success" v-on:click="addAuthor"></b-icon>
            </b-input-group-append>
        </b-input-group>
      </b-form-group>
      <b-button type="submit" variant="primary">Submit</b-button>
      <b-button type="reset" variant="danger">Cancel</b-button>
    </b-form>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        book: {
          isbn: "",
          title: "",
          authors: [""]
        }
      }
    },
    methods: {
      onSubmit() {
        console.log(this.book)
        alert(this.book)
      },
      addAuthor() {
        this.book.authors.push("")
      },
      removeAuthor(author) {
        let authors = this.book.authors
        if(authors.length > 1){
          const indexOfAuthor = authors.indexOf(author)
          authors.splice(indexOfAuthor, 1)
        }
      },
      authorPlaceholder(index) {
        return `Author#${index + 1}`
      }
    },
    computed: {
      isbnValidation() {
        const regex = /^\d{10}$|^\d{13}$/
        return regex.test(this.book.isbn)
      }      
    }
  }
</script>

<style scoped>

</style>