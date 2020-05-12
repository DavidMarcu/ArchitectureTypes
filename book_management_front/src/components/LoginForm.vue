<template>
  <v-content>
    <v-container class="fill-height" fluid>
      <v-row align="center" justify="center">
        <v-col cols="12" sm="8" md="4">
          <v-card class="elevation-12">
            <v-toolbar color="primary" dark flat>
              <v-toolbar-title>Login form</v-toolbar-title>
              <v-spacer/>
            </v-toolbar>
            <v-card-text>
              <v-form id="user-login" @submit.stop.prevent="onSubmit">
                <v-text-field
                        id="username"
                        label="Username"
                        name="username"
                        v-model="username"
                        type="text"
                        prepend-icon="mdi-account"/>

                <v-text-field
                        id="password"
                        label="Password"
                        name="password"
                        v-model="password"
                        prepend-icon="mdi-lock"
                        type="password"/>
              </v-form>
              <div class="d-flex info-container">
                <span class="ml-3 red--text">
                  {{loginError}}
                </span>
                <router-link tag="a" :to="'/signup'" class="info--text">Create account</router-link>
              </div>
            </v-card-text>

            <v-card-actions>
              <v-spacer/>
              <v-btn type="submit" form="user-login" color="primary">Login</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-content>
</template>

<script>
  export default {
    data() {
      return {
        username: "",
        password: "",
        loginError: ""
      }
    },
    methods: {
      async onSubmit() {
        try {
          let user = {
            username: this.username,
            password: this.password,
          }
          await this.$store.dispatch('loginUser', user)
          await this.$router.push('/').then(() => location.reload())
        }
        catch (error) {
          if(error.status === 401) this.loginError = error.data.message
          else console.error(error)
        }
      }
    }
  }
</script>
<style scoped>
  .info-container {
    justify-content: space-between;
  }
</style>
