<template>
  <v-app id="inspire">
    <v-app-bar :clipped-left="$vuetify.breakpoint.lgAndUp" app color="primary" dark>
      <v-toolbar-title style="width: 350px">
        <a href="/" class="white--text" style="text-decoration: none"><v-icon>mdi-bookshelf</v-icon>&nbsp;MiLib</a>
      </v-toolbar-title>
      <v-text-field
        flat
        solo-inverted
        hide-details
        prepend-inner-icon="mdi-magnify"
        label="Search"
        class="hidden-sm-and-down pl-10 ml-4"
        v-show="isLoggedIn"/>
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
    <v-content v-if="isLoggedIn">
      <v-bottom-navigation
        :value="activeBtn"
        color="primary"
        horizontal>
        <a href="/" class="v-btn">
          <span>Home</span>
        </a>
        <a href="/shop" class="v-btn">
            <span>Shop</span>
        </a>
        <a href="/product" class="v-btn">
          <span>Product</span>
        </a>
        <v-btn href="/blog">
          <span>Blog</span>
        </v-btn>
      </v-bottom-navigation>
    </v-content>
      <router-view/>
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

