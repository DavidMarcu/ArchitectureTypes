<template>
  <div ref="reviewContainer" class="d-inline-block">
    <v-dialog v-model="dialog" max-width="600px">
      <template v-slot:activator="{ on }">
        <v-btn color="info" outlined v-on="on">
          <span v-if="ownership">EDIT REVIEW</span>
          <span v-else>ADD REVIEW</span>
        </v-btn>
      </template>
      <v-card>
        <v-form v-model="valid" @submit.prevent="" lazy-validation id="review-form" ref="reviewForm">
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
                            color="warning" v-model="rating" hover></v-rating>
                </v-col>
                <v-col cols="12">
                  <v-textarea label="Review" v-model="review"></v-textarea>
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
  import addNotification from "../helpers/NotificationHelper";
  import notifications from "../helpers/NotificationProperties";
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
      }
    },
    methods: {
      onAddReview() {
        const reviewBody = {
          isbn: this.isbn,
          rating: this.rating,
          review: this.review
        }
        reviewService.addReviewForBook(reviewBody)
            .then(() => {
              this.$refs.reviewContainer.appendChild(addNotification(notifications.SUCCESSFUL_REVIEW_ADDED).$el)
              this.$emit('rated', {review: this.review, rating: this.rating, status: "added"})
              this.dialog = false
            })
            .catch(error => console.error(error))
      },
      onUpdateReview() {
        const reviewBody = {
          isbn: this.isbn,
          rating: this.rating,
          review: this.review
        }
        reviewService.updateReviewForBook(reviewBody)
           .then(() => {
             this.$refs.reviewContainer.appendChild(addNotification(notifications.SUCCESSFUL_REVIEW_UPDATED).$el)
             this.$emit('rated', {review: this.review, rating: this.rating, status: "edited"})
             this.dialog = false
           })
           .catch(error => console.error(error))
      },
      onDeleteReview() {
        reviewService.deleteReviewForBook(this.isbn)
          .then(() => {
            this.$refs.reviewContainer.appendChild(addNotification(notifications.SUCCESSFUL_REVIEW_DELETED).$el)
            this.$emit('rated', {status: "deleted"})
            this.dialog = false
          })
          .catch(error => console.error(error))
      }
    }
  }
</script>

<style scoped>

</style>
