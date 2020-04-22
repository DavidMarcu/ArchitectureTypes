<template>
  <div>
    <router-link class="bookCardLink" v-bind:to="{name: 'bookDetail'}">
    <b-card
      v-bind:title="bookTitle"
      title-tag="h3"
      v-bind:sub-title="authors.toString()"
      v-bind:img-src="imgSrc"
      img-alt="book cover"
      img-top
      img-height="400px"
      tag="article"
      style="max-width: 20rem; height: 800px"
      class="mb-2 bookCard"
    >
      <b-card-text>
        {{shortBookDescription}}
      </b-card-text>
    </b-card>
    </router-link>
  </div>
</template>

<script>
  export default {
    props: {
      imgSrc: {
        type: String,
        default: "https://picsum.photos/600/300/?image=25"
      },
      bookTitle: String,
      bookDescription: String,
      authors: Array
    },
    computed: {
      shortBookDescription() {
        let bookDescription = this.bookDescription
        return bookDescription.length > 200 ? bookDescription.substring(0, 201) + "..." : bookDescription
      }
    }
  }
</script>

<style scoped>

.bookCardLink{
  text-decoration: none;
  color: black;
  font-weight: 100
}

.bookCard:hover {
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2), 0 1px 15px 0 rgba(0, 0, 0, 0.19);
}

</style>