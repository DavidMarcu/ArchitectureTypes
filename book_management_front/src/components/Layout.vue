<template>
  <v-app id="inspire">
    <v-app-bar :clipped-left="$vuetify.breakpoint.lgAndUp" app color="primary" dark>
      <v-toolbar-title style="width: 350px">
        <a href="/" class="white--text" style="text-decoration: none"><v-icon>mdi-bookshelf</v-icon>&nbsp;MiLib</a>
      </v-toolbar-title>
      <v-spacer />
      <v-menu open-on-hover offset-y>
        <template v-slot:activator="{ on }">
          <v-btn v-on="on" icon v-show="isLoggedIn">
            <v-icon>mdi-account-circle</v-icon>
          </v-btn>
        </template>
        <v-list v-for="(item, index) in items" :key="index" class="mx-auto my-0" outlined>
            <v-list-item @click="onLogout">{{item.title}}</v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>
    <v-content>
      <router-view/>
    </v-content>
  </v-app>
</template>
<script>
  export default {
    data (){
      return {
        activeBtn: 1,
        items: [
          {
            title: "Logout"
          }]
      }
    },
    methods: {
      onLogout() {
        sessionStorage.clear()
        location.reload()
      }
    },
    computed: {
      isLoggedIn() {
        return this.$store.getters.isLoggedIn
      }
    }
  }
</script>

