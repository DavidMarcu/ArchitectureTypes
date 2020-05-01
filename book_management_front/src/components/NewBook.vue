<template>
  <div>
    <v-dialog v-model="dialog" persistent max-width="600px">
      <template v-slot:activator="{ on }">
        <v-btn color="success" v-on="on">
          <v-icon>mdi-book-plus</v-icon>
          Add new book
        </v-btn>
      </template>
      <v-card>
        <v-form id="book-form" @submit.prevent="onSubmit" ref="newBook" v-model="valid" lazy-validation>
          <v-card-title>
            <span class="headline">New Book</span>
          </v-card-title>
          <v-card-text>

            <v-container>
              <v-row dense>
                <v-col cols="12">
                  <v-text-field :rules="isbnRules" v-model="isbn" label="ISBN*"
                                required></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field v-model="title" :rules="requiredRule" label="Title*" required></v-text-field>
                </v-col>
                <v-col v-for="(author, index) in authors" :key="index" cols="12">
                  <v-text-field v-model="author.value" :rules="requiredRule" :label="authorPlaceholder(index)" required>
                    <template slot="append-outer">
                      <v-icon v-show="shouldAppendIconPlus(index)"
                              @click="addAuthor"
                              class="mr-2">
                        mdi-account-plus-outline
                      </v-icon>
                      <v-icon v-show="shouldAppendIconMinus()"
                              @click="removeAuthor(author)">
                        mdi-account-minus-outline
                      </v-icon>
                    </template>
                  </v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-file-input :rules="photoRule"
                                v-model="coverImage"
                                accept="image/jpeg, image/png"
                                label="Cover(PNG or JPG format)">
                  </v-file-input>
                </v-col>
                <v-col cols="12">
                  <v-textarea v-model="description" label="Desciption"></v-textarea>
                </v-col>
                <v-col cols="12" sm="6">
                  <v-text-field :rules="editionRule" type="number" v-model="editionNumber" label="Edition no"></v-text-field>
                </v-col>
              </v-row>
            </v-container>
            <small>*indicates required field</small>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn type="submit" color="blue darken-1" text form="book-form">Save</v-btn>
            <v-btn color="blue darken-1" text @click="onClose">Close</v-btn>
          </v-card-actions>
        </v-form>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        valid: false,
        dialog: false,
        isbn: "",
        title: "",
        authors: [{value: ""}],
        coverImage: null,
        description: "",
        editionNumber: null,
        isbnRules: [
          value => /^\d{10}$|^\d{13}$/.test(value) || "Only 10 or 13 digits",
        ],
        requiredRule: [
          value => !!value || "Can't be empty"
        ],
        editionRule: [
            value => value != null ? value >= 1 : true || "Edition number must be greater than 1"
        ],
        photoRule: [
            value => value != null ? ["image/jpeg", "image/png"].includes(value.type) : true || "Image must be in JPG or PNG format"
        ]
      }
    },
    methods: {
      async onSubmit() {
        if (this.validate()) {
          let base64function = this.getBase64Bytes(this.coverImage)
          let book = {
            isbn: this.isbn,
            title: this.title,
            authors: this.authors.map(author => author.value),
            description: this.description,
            editionNumber: this.editionNumber,
            coverImage: this.coverImage != null ? base64function : null
          }
          if(book.coverImage != null) {
            let base64FunctionResult = await base64function
            book.coverImage = base64FunctionResult.fileAsBase64
            const splitIndex = book.coverImage.indexOf("base64")
            book.coverImage = base64FunctionResult.fileAsBase64.substr(splitIndex + 7)
            if (base64FunctionResult === "image/jpeg")
              book.coverImageType = "jpg"
            else if (base64FunctionResult === "image/png")
              book.coverImageType = "png"
          }
          this.dialog = false
          await this.$store.dispatch("insertBook", book)
        }
      },
      onClose() {
        this.$refs.newBook.reset()
        this.dialog = false
      },
      addAuthor() {
        this.authors.push({value: ""})
      },
      removeAuthor(author) {
        let authors = this.authors
        if (authors.length > 1) {
          const indexOfAuthor = authors.indexOf(author)
          authors.splice(indexOfAuthor, 1)
        }
      },
      authorPlaceholder(index) {
        return `Author#${index + 1}` + (index === 0 ? "*" : "")
      },
      shouldAppendIconPlus(index) {
        return index === this.authors.length - 1
      },
      shouldAppendIconMinus() {
        return this.authors.length > 1
      },
      validate() {
        return this.$refs.newBook.validate()
      },
      getBase64Bytes(file) {
        return file != null ? new Promise(function (resolve, reject) {
          let fileReader = new FileReader()
          fileReader.onload = function() { resolve({fileAsBase64: fileReader.result, filetype: file.type}) }
          fileReader.onerror = reject
          fileReader.readAsDataURL(file)
        }) : null
      }
    },
  }
</script>

<style scoped>

</style>
