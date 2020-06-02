const notifications = {
  DUPLICATE_BOOK: {type: "error", text: "Failure! This is a duplicate book"},
  SUCCESSFUL_BOOK: {type: "success", text: "Success! Book added successfully"},
  SUCCESSFUL_BOOK_TO_USER: {type: "success", text: "Success! This book is now in your library."},
  SUCCESSFUL_BOOK_REMOVE_FROM_USER: {type: "success", text: "Success! This book is no more in your library"},
  SUCCESSFUL_REVIEW_ADDED: {type: "success", text: "Review added successfully!"},
  SUCCESSFUL_REVIEW_UPDATED: {type: "success", text: "Review edited successfully!"},
  SUCCESSFUL_REVIEW_DELETED: {type: "success", text: "Review deleted successfully!"}
}

export default notifications
