<template>
  <div>
    <v-container>
      <div class="row">
        <div class="col-lg-3 col-md-5 col-sm-5 col-xs-12">
          <v-img :aspect-ratio="0.75" :src="imageSource"></v-img>
        </div>
        <div class="col-lg-9 col-md-7 col-sm-7 col-xs-12">
          <div class="pl-6">
            <p class="display-1 mb-0">{{book.title}}</p>
            <v-card-actions class="pa-0">
              <p class="headline font-weight-light pt-3">{{book.authors.toString()}}</p>
              <v-spacer></v-spacer>
              <v-rating readonly :value="rating" half-increments background-color="warning lighten-3"
                        color="warning" dense></v-rating>
              <span v-if="this.numberOfReviews === 1" class="body-2	font-weight-thin"> 1 REVIEW</span>
              <span v-else class="body-2	font-weight-thin"> {{this.numberOfReviews}} REVIEWS</span>
            </v-card-actions>
            <p class="subtitle-1">
              {{book.description}}
            </p>

            <ReviewModal v-if="hasBook"
                         :rating="ownReview !== null ? ownReview.rating : 0"
                         :review="ownReview !== null ? ownReview.review : ''"
                         :ownership="ownReview !== null"
                         :isbn="this.isbn"
                         @rated="onReviewChange"></ReviewModal>
            <v-btn v-if="hasBook" class="ml-4" color="error" @click="onRemove" outlined tile>REMOVE FROM MY LIBRARY</v-btn>
            <v-btn v-else class="ml-4" @click="onAdd" outlined tile>ADD TO MY LIBRARY</v-btn>
          </div>

        </div>
      </div>
      <div class="row">
        <div class="col-sm-12 col-xs-12 col-md-12">
          <v-tabs>
            <v-tab>REVIEWS</v-tab>
            <v-tab-item>
              <v-list :three-line="true">
                <v-list-item-group v-model="item" color="primary">
                  <v-list-item
                          v-for="review in reviews"
                          :key="review.reviewId"
                          :inactive="true"
                  >
                    <v-list-item-content>
                      <v-list-item-title v-html="review.username"></v-list-item-title>
                      <v-rating readonly :value="review.rating" small
                                background-color="warning lighten-3"
                                color="warning" dense></v-rating>
                      <v-list-item-subtitle v-html="review.review"></v-list-item-subtitle>
                    </v-list-item-content>
                  </v-list-item>
                </v-list-item-group>
              </v-list>
              <v-btn v-if="page < lastPage" text small @click="addReviews">show more</v-btn>
            </v-tab-item>
          </v-tabs>
        </div>
      </div>
    </v-container>
  </div>
</template>
<script>
  import bookService from '@/service/BookService.js';
  import reviewService from '@/service/ReviewService.js';
  import ReviewModal from '@/components/ReviewModal.vue';
  import notifications from "../helpers/NotificationProperties";

  export default {
    props: ['isbn'],
    components: {
      ReviewModal
    },
    created() {
      bookService.getBookByIsbn(this.isbn)
          .then(response => {
            this.book = response.data
          })
          .catch(error => console.error(error));
      bookService.getBookOwnership(this.isbn)
          .then(() => this.hasBook = true)
          .catch(error => {
            if(error.response.status === 404) {
              this.hasBook = false
            }
            else {
              console.error(error)
              this.hasBook = false
            }
          })
      reviewService.getReviewsForBook(this.isbn, this.page)
        .then(response => {
          this.reviews = response.data.otherReviews
          this.ownReview = response.data.ownReview
          this.numberOfReviews = response.data.reviewCount
          this.rating = response.data.ratingAvg
          this.lastPage = response.data.lastPage
        })
        .catch(error => console.error(error))
    },
    data() {
      return {
        book: null,
        rating: 0,
        item: 5,
        reviews: [],
        numberOfReviews: 0,
        ownReview: null,
        hasBook: false,
        page: 1,
        lastPage: 1
      }
    },
    computed: {
      imageSource() {
        return this.book !== null ? `data:image/${this.book.coverImageType};base64,${this.book.coverImage}` :
            'https://i.imgur.com/J5LVHEL.jpg'
      }
    },
    methods: {
      onRemove() {
        bookService.removeBookForUser(this.book.isbn)
          .then(() => {
            this.$router.push('/')
            this.$store.dispatch('emitNotification', notifications.SUCCESSFUL_BOOK_REMOVE_FROM_USER)
          })
          .catch(error => console.error(error))
      },
      onAdd() {
        const bookToBeAdded = {isbn: this.book.isbn}
        bookService.addBookForUser(bookToBeAdded)
          .then(() => {
            this.$router.push('/')
            this.$store.dispatch('emitNotification', notifications.SUCCESSFUL_BOOK_TO_USER)
          })
          .catch(error => console.error(error))
      },
      addReviews() {
          reviewService.getReviewsForBook(this.isbn, ++this.page)
            .then(response => this.reviews = this.reviews.concat(response.data.otherReviews))
            .catch(error => console.error(error))
      },
      onReviewChange(newRating) {
        switch (newRating.status) {
          case "edited":
            this.rating = (this.rating + newRating.rating - this.ownReview.rating) / this.numberOfReviews;
            this.ownReview = {
              rating: newRating.rating,
              review: newRating.review
            }
            break
          case "deleted":
            this.numberOfReviews--;
            this.rating = this.numberOfReviews > 0 ? (this.rating - this.ownReview.rating) / this.numberOfReviews : 0;
            this.ownReview = null
            break
          case "added":
            this.numberOfReviews++;
            this.rating = (this.rating + newRating.rating) / this.numberOfReviews
            this.ownReview = {
              rating: newRating.rating,
              review: newRating.review
            }
            break
        }
        console.log(newRating)
      }
    }
  }
</script>
