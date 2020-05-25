<template>
  <div class="d-inline-block">
    <v-dialog @click:outside="onCancel" v-model="dialog" max-width="600px">
      <template v-slot:activator="{ on }">
        <v-btn color="info" outlined v-on="on">
          <span v-if="ownership">EDIT REVIEW</span>
          <span v-else>ADD REVIEW</span>
        </v-btn>
      </template>
      <v-card>
        <v-form v-model="valid" lazy-validation id="review-form" ref="reviewForm">
          <v-card-title>
            <span class="headline">Review</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row dense>
                <v-col cols="12">
                  <span class="body-1	font-weight-light">Rating: </span>
                </v-col>
                <v-col cols="12">
                  <v-rating background-color="warning lighten-3"
                            color="warning" v-model="formRating" hover></v-rating>
                </v-col>
                <v-col cols="12">
                  <v-textarea label="Review" v-model="formReview"></v-textarea>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn v-if="ownership" type="submit" color="red darken-1" text form="review-form" @click="onDeleteReview">
              Delete
            </v-btn>
            <v-btn v-if="ownership" type="submit" color="blue darken-1" text form="review-form" @click="onUpdateReview">
              Update
            </v-btn>
            <v-btn v-else type="submit" color="blue darken-1" text form="review-form" @click="onAddReview">
              Add
            </v-btn>
          </v-card-actions>
        </v-form>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
  import reviewService from '@/service/ReviewService.js';
  export default {
    props: {
      isbn: String,
      ownership: Boolean,
      rating: {
        type: Number,
      },
      review: {
        type: String,
      }
    },
    name: "ReviewModal",
    data() {
      return {
        dialog: false,
        valid: false,
        formRating: this.rating,
        formReview: this.review
      }
    },
    methods: {
      onAddReview() {
        const reviewBody = {
          isbn: this.isbn,
          rating: this.formRating,
          review: this.formReview
        }
        reviewService.addReviewForBook(reviewBody)
            .then(response => {
              this.rating = response.data.rating
              this.review = response.data.review
              this.dialog = false
            })
            .catch(error => console.error(error))
      },
      onUpdateReview() {
        const reviewBody = {
          isbn: this.isbn,
          rating: this.formRating,
          review: this.formReview
        }
        reviewService.updateReviewForBook(reviewBody)
           .then(response => {
             this.rating = response.data.rating
             this.review = response.data.review
             this.dialog = false
           })
           .catch(error => console.error(error))
      },
      onDeleteReview() {
        reviewService.deleteReviewForBook(this.isbn)
          .then(() => {
            this.rating = 0
            this.review = ""
            this.dialog = false
          })
          .catch(error => console.error(error))
      },
      onCancel() {
        this.formRating = this.rating
        this.formReview = this.review
      }
    }
  }
</script>

<style scoped>

</style>
