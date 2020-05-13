<template>
  <div>
    <v-container>
      <div class="row">
        <div class="col-lg-3 col-md-5 col-sm-5 col-xs-12">
          <v-img :aspect-ratio="0.75" :src="imageSource"></v-img>
        </div>
        <div class="col-lg-9 col-md-7 col-sm-7 col-xs-12">
          <v-breadcrumbs class="pb-0" :items="breadcrums"></v-breadcrumbs>
          <div class="pl-6">
            <p class="display-1 mb-0">{{book.title}}</p>
            <v-card-actions class="pa-0">
              <p class="headline font-weight-light pt-3">{{book.authors.toString()}}</p>
              <v-spacer></v-spacer>
              <v-rating v-model="rating" class="" background-color="warning lighten-3"
                        color="warning" dense></v-rating>
              <span class="body-2	font-weight-thin"> 25 REVIEWS</span>
            </v-card-actions>
            <p class="subtitle-1 font-weight-thin">
              {{book.description}}
            </p>
            <v-btn class="primary white--text" outlined tile dense><v-icon>mdi-cart</v-icon> ADD TO CART</v-btn>
            <v-btn class="ml-4" outlined tile>ADD TO WISHLIST</v-btn>
          </div>

        </div>
      </div>
      <div class="row">
        <div class="col-sm-12 col-xs-12 col-md-12">
          <v-tabs>
            <v-tab>REVIEWS</v-tab>
            <v-tab-item>
              <v-list three-line="true" avatar="true">
                <v-list-item-group v-model="item" color="primary">
                  <v-list-item
                          v-for="(item, i) in items"
                          :key="i"
                          inactive="true"
                  >
                    <v-list-item-avatar>
                      <v-img :src="item.avatar"></v-img>
                    </v-list-item-avatar>
                    <v-list-item-content>
                      <v-list-item-title v-html="item.title"></v-list-item-title><v-rating v-model="rating" class="" background-color="warning lighten-3"
                                                                                           color="warning" dense></v-rating>
                      <v-list-item-subtitle v-html="item.subtitle"></v-list-item-subtitle>
                    </v-list-item-content>
                  </v-list-item>
                </v-list-item-group>
              </v-list>
            </v-tab-item>
          </v-tabs>
        </div>
      </div>
    </v-container>
  </div>
</template>
<script>
  import bookService from '@/service/BookService.js';
  export default {
    props: ['isbn'],
    created() {
      bookService.getBookByIsbn(this.isbn)
          .then(response => this.book = response.data)
          .catch(error => console.error(error))
    },
    data() {
      return {
        book: null,
        rating: 4.5,
        item: 1,
        items: [
          {
            avatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
            title: 'Lorem ipsum dolor?',
            subtitle: "<span class='text--primary'>Ali Connors</span> &mdash; Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Nisl tincidunt eget nullam non. Tincidunt arcu non sodales neque sodales ut etiam. Lectus arcu bibendum at varius vel pharetra. Morbi tristique senectus et netus et malesuada.\n" +
                "\n",
          }
        ]
      }
    },
    computed: {
      imageSource() {
        return this.book !== null ? `data:image/${this.book.coverImageType};base64,${this.book.coverImage}` :
            'placeholder'
      }
    }
  }
</script>
