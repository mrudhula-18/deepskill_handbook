import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

/**
 * GET /books?page=&size= -- paginated list of books.
 */
export function listBooks(page = 0, size = 10) {
  return apiClient.get('/books', { params: { page, size, sort: 'title,asc' } }).then((res) => res.data);
}

/**
 * GET /books/{id} -- a single book.
 */
export function getBook(id) {
  return apiClient.get(`/books/${id}`).then((res) => res.data);
}

/**
 * GET /books/search?keyword= -- search by title/author/isbn.
 */
export function searchBooks(keyword) {
  return apiClient.get('/books/search', { params: { keyword } }).then((res) => res.data);
}

/**
 * POST /books -- create a book.
 */
export function createBook(dto) {
  return apiClient.post('/books', dto).then((res) => res.data);
}

/**
 * PUT /books/{id} -- update a book.
 */
export function updateBook(id, dto) {
  return apiClient.put(`/books/${id}`, dto).then((res) => res.data);
}

/**
 * DELETE /books/{id} -- delete a book.
 */
export function deleteBook(id) {
  return apiClient.delete(`/books/${id}`).then((res) => res.data);
}

/**
 * GET /authors -- list authors (used to populate form dropdowns).
 */
export function listAuthors() {
  return apiClient.get('/authors').then((res) => res.data);
}

/**
 * GET /categories -- list categories (used to populate form dropdowns).
 */
export function listCategories() {
  return apiClient.get('/categories').then((res) => res.data);
}

export default apiClient;
