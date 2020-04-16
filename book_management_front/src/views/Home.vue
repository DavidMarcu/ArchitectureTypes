<template>
  <div>
    <b-container fluid>
      <b-row>
          <b-col><BookElement/></b-col>
          <b-col><BookElement/></b-col>
          <b-col><BookElement/></b-col>
          <b-col><BookElement/></b-col>
          <b-col><BookElement/></b-col>
          <b-col><BookElement/></b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
  import BookElement from '@/components/BookElement.vue';
  export default {
    components: {
      BookElement
    }
  }
</script>

<style scoped>

</style>