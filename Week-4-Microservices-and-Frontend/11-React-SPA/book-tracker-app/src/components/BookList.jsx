import { useCallback, useEffect, useState } from 'react'
import bookApi from '../services/bookApi'
import BookItem from './BookItem.jsx'

/**
 * Fetches the book list on mount and whenever `refreshKey` changes
 * (App.jsx bumps refreshKey after a successful add). Demonstrates
 * useEffect + useState for data fetching, loading/empty/error
 * conditional rendering, and .map() with a stable `key` for lists.
 */
function BookList({ refreshKey, onBooksLoaded }) {
  const [books, setBooks] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  const loadBooks = useCallback(async () => {
    setLoading(true)
    setError('')
    try {
      const data = await bookApi.getAllBooks()
      setBooks(data)
      if (onBooksLoaded) onBooksLoaded(data)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }, [onBooksLoaded])

  useEffect(() => {
    loadBooks()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [refreshKey])

  if (loading) {
    return <p className="status-text">Loading books...</p>
  }

  if (error) {
    return <p className="error-text">{error}</p>
  }

  if (books.length === 0) {
    return <p className="status-text">No books in your library yet.</p>
  }

  return (
    <ul className="book-list">
      {books.map((book) => (
        <BookItem key={book.id} book={book} onDelete={loadBooks} />
      ))}
    </ul>
  )
}

export default BookList
