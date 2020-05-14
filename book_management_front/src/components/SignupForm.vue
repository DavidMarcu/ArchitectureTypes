<template>
  <v-content>
    <v-container class="fill-height" fluid>
      <v-row align="center" justify="center">
        <v-col cols="12" sm="8" md="4">
          <v-card class="elevation-12">
            <v-toolbar color="primary" dark flat>
              <v-toolbar-title>Signup form</v-toolbar-title>
              <v-spacer/>
            </v-toolbar>
            <v-card-text>
              <v-form id="user-signup" ref="signupForm" v-model="valid" @submit.stop.prevent="onSubmit" lazy-validation>
                <v-text-field
                        id="username"
                        label="Username"
                        name="username"
                        v-model="username"
                        :rules="usernameRules"
                        type="text"
                        prepend-icon="mdi-account"/>

                <v-text-field
                        id="email"
                        label="Email"
                        name="email"
                        v-model="email"
                        :rules="emailRule"
                        prepend-icon="mdi-at"
                        type="email"/>

                <v-text-field
                        id="password"
                        label="Password"
                        name="password"
                        v-model="password"
                        :rules="passwordRule"
                        prepend-icon="mdi-lock"
                        type="password"/>

                <v-text-field
                        id="repassword"
                        label="Repeat password"
                        name="repassword"
                        v-model="repassword"
                        :rules="repasswordRules"
                        prepend-icon="mdi-lock"
                        type="password"/>

              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-spacer/>
              <v-btn type="submit" form="user-signup" color="primary">Signup</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
    <Notification v-if="showingNotification" type="error">{{errorText}}</Notification>
  </v-content>
</template>

<script>
  /* eslint-disable no-unused-vars */

  import userService from "../service/UserService";
  import Notification from '@/components/Notification.vue';
  export default {
    components: {
      Notification
    },
    data() {
      return {
        valid: true,
        username: "",
        usernameRules: [
          username => username.length >= 4 && username.length <= 32 || 'Username must be between 4 and 32 characters',
          username => /^[a-zA-Z0-9\-_]+$/.test(username) || 'Only alphanumeric characters and "-" or "_" allowed'
        ],
        password: "",
        passwordRule: [
          password => password.length >= 6 || 'Password should be at least 6 characters long'
        ],
        repassword: "",
        repasswordRules: [
          repassword => repassword === this.password || 'Password mismatch'
        ],
        email: "",
        emailRule: [
          email => /[^@ \t\r\n]+@[^@ \t\r\n]+\.[^@ \t\r\n]+/.test(email) || 'Invalid email format'
        ],
        errorText: null,
        showingNotification: false
      }
    },
    methods: {
      onSubmit() {
        if(this.$refs.signupForm.validate()) {
          let user = {
            username: this.username,
            password: this.password,
            email: this.email
          }
          userService.signup(user)
              .then(() => this.$router.push('/'))
              .catch(error => {this.showingNotification = true; this.errorText = error.response.data.message})
        }
      }
    }
  }
</script>
