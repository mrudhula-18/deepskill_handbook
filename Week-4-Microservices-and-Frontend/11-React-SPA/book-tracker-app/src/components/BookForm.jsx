import { useState } from 'react'
import bookApi from '../services/bookApi'

const initialForm = { title: '', author: '', isbn: '', price: '' }

/**
 * Controlled form (single state object holds every field). Demonstrates
 * form handling, client-side validation with inline error messages, and
 * calling the API service on submit.
 */
function BookForm({ onBookAdded }) {
  const [form, setForm] = useState(initialForm)
  const [errors, setErrors] = useState({})
  const [submitting, setSubmitting] = useState(false)
  const [submitError, setSubmitError] = useState('')

  const handleChange = (event) => {
    const { name, value } = event.target
    setForm((prev) => ({ ...prev, [name]: value }))
  }

  const validate = () => {
    const nextErrors = {}

    if (!form.title.trim()) nextErrors.title = 'Title is required.'
    if (!form.author.trim()) nextErrors.author = 'Author is required.'
    if (!form.isbn.trim()) nextErrors.isbn = 'ISBN is required.'

    const priceValue = Number(form.price)
    if (form.price.trim() === '' || Number.isNaN(priceValue) || priceValue <= 0) {
      nextErrors.price = 'Price must be a positive number.'
    }

    setErrors(nextErrors)
    return Object.keys(nextErrors).length === 0
  }

  const handleSubmit = async (event) => {
    event.preventDefault()
    setSubmitError('')

    if (!validate()) return

    setSubmitting(true)
    try {
      await bookApi.createBook({
        title: form.title.trim(),
        author: form.author.trim(),
        isbn: form.isbn.trim(),
        price: Number(form.price),
      })
      setForm(initialForm)
      setErrors({})
      onBookAdded()
    } catch (err) {
      setSubmitError(err.message)
    } finally {
      setSubmitting(false)
    }
  }

  return (
    <form className="book-form" onSubmit={handleSubmit}>
      <div className="form-field">
        <label htmlFor="title">Title</label>
        <input id="title" name="title" value={form.title} onChange={handleChange} />
        {errors.title && <p className="error-text">{errors.title}</p>}
      </div>

      <div className="form-field">
        <label htmlFor="author">Author</label>
        <input id="author" name="author" value={form.author} onChange={handleChange} />
        {errors.author && <p className="error-text">{errors.author}</p>}
      </div>

      <div className="form-field">
        <label htmlFor="isbn">ISBN</label>
        <input id="isbn" name="isbn" value={form.isbn} onChange={handleChange} />
        {errors.isbn && <p className="error-text">{errors.isbn}</p>}
      </div>

      <div className="form-field">
        <label htmlFor="price">Price</label>
        <input
          id="price"
          name="price"
          type="number"
          step="0.01"
          value={form.price}
          onChange={handleChange}
        />
        {errors.price && <p className="error-text">{errors.price}</p>}
      </div>

      <button type="submit" disabled={submitting}>
        {submitting ? 'Adding...' : 'Add Book'}
      </button>

      {submitError && <p className="error-text">{submitError}</p>}
    </form>
  )
}

export default BookForm
