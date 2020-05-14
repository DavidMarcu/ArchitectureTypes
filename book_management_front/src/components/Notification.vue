<template>
  <v-snackbar
      v-model="notification"
      bottom
      :timeout="4000"
      :color="type">
    <slot></slot>
    <v-btn dark text @click="snackbar = false">
      Close
    </v-btn>
  </v-snackbar>
</template>

<script>
  export default {
    name: "Notification",
    props: ['type'],
    data() {
      return {
        notification: true
      }
    }
  }
</script>

<style scoped>

</style>
