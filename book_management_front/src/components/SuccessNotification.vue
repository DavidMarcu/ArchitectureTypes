<template>
  <v-snackbar
      v-model="notification"
      bottom
      color="success">
    Success!!!
  </v-snackbar>
</template>

<script>
  export default {
    name: "SuccessNotification",
    data() {
      return {
        notification: true
      }
    }
  }
</script>

<style scoped>

</style>
