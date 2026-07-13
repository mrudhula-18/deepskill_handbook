import { useState } from 'react'
import bookApi from '../services/bookApi'

/**
 * Renders a single book. Receives `book` and `onDelete` as props (props
 * demo) and owns just enough local state (`useState`) to track its own
 * in-flight delete request.
 */
function BookItem({ book, onDelete }) {
  const [deleting, setDeleting] = useState(false)
  const [error, setError] = useState('')

  const handleDelete = async () => {
    setDeleting(true)
    setError('')
    try {
      await bookApi.deleteBook(book.id)
      onDelete()
    } catch (err) {
      setError(err.message)
      setDeleting(false)
    }
  }

  return (
    <li className="book-item">
      <div className="book-item-details">
        <strong>{book.title}</strong> by {book.author}
        <span className="book-price"> - ${Number(book.price).toFixed(2)}</span>
      </div>
      <button onClick={handleDelete} disabled={deleting}>
        {deleting ? 'Deleting...' : 'Delete'}
      </button>
      {error && <p className="error-text">{error}</p>}
    </li>
  )
}

export default BookItem
