import axios from 'axios'

// A single configured axios instance. Every call in this module reuses
// this baseURL instead of hardcoding the backend host/port inline, so
// swapping backends only means editing .env.
const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
})

function toErrorMessage(error, fallback) {
  if (error.response) {
    // Server responded with a non-2xx status.
    const serverMessage = error.response.data && error.response.data.message
    return serverMessage || `${fallback} (status ${error.response.status})`
  }
  if (error.request) {
    // Request was made but no response was received (backend down, CORS, etc.)
    return `${fallback}: no response from server. Is the backend running?`
  }
  // Something went wrong building/sending the request itself.
  return `${fallback}: ${error.message}`
}

export const bookApi = {
  /**
   * GET /api/books - returns the full list of books.
   */
  async getAllBooks() {
    try {
      const response = await apiClient.get('/books')
      return response.data
    } catch (error) {
      throw new Error(toErrorMessage(error, 'Failed to fetch books'))
    }
  },

  /**
   * POST /api/books - creates a new book.
   * @param {{title: string, author: string, isbn: string, price: number}} book
   */
  async createBook(book) {
    try {
      const response = await apiClient.post('/books', book)
      return response.data
    } catch (error) {
      throw new Error(toErrorMessage(error, 'Failed to create book'))
    }
  },

  /**
   * DELETE /api/books/{id} - deletes a book by id.
   */
  async deleteBook(id) {
    try {
      await apiClient.delete(`/books/${id}`)
    } catch (error) {
      throw new Error(toErrorMessage(error, `Failed to delete book ${id}`))
    }
  },
}

export default bookApi
