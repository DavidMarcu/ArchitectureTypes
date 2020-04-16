<template>
  <div>
    <router-link class="bookCardLink" v-bind:to="{name: 'bookDetail'}">
    <b-card
      title="Card Title"
      img-src="https://picsum.photos/600/300/?image=25"
      img-alt="Image"
      img-top
      tag="article"
      style="max-width: 20rem;"
      class="mb-2 bookCard"
    >
      <b-card-text>
        Some quick example text to build on the card title and make up the bulk of the card's content.
      </b-card-text>

      <!-- <b-button href="#" variant="primary">Go somewhere</b-button> -->
    </b-card>
    </router-link>
  </div>
</template>

<script>
  export default {
    
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