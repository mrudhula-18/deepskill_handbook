import { useEffect, useState, useCallback } from 'react';
import { Link } from 'react-router-dom';
import { listBooks, searchBooks, deleteBook } from '../services/bookApi.js';
import LoadingSpinner from '../components/LoadingSpinner.jsx';
import ErrorBanner from '../components/ErrorBanner.jsx';

function extractErrorMessage(err) {
  return err?.response?.data?.message || err?.message || 'Something went wrong.';
}

function BookListPage() {
  const [books, setBooks] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [keyword, setKeyword] = useState('');
  const [isSearchMode, setIsSearchMode] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const loadPage = useCallback(async (pageToLoad) => {
    setLoading(true);
    setError('');
    try {
      const data = await listBooks(pageToLoad, 10);
      setBooks(data.content || []);
      setTotalPages(data.totalPages ?? 0);
      setPage(data.number ?? pageToLoad);
      setIsSearchMode(false);
    } catch (err) {
      setError(extractErrorMessage(err));
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    loadPage(0);
  }, [loadPage]);

  const handleSearchSubmit = async (event) => {
    event.preventDefault();
    if (!keyword.trim()) {
      loadPage(0);
      return;
    }
    setLoading(true);
    setError('');
    try {
      const results = await searchBooks(keyword.trim());
      setBooks(results || []);
      setIsSearchMode(true);
    } catch (err) {
      setError(extractErrorMessage(err));
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this book?')) {
      return;
    }
    setError('');
    try {
      await deleteBook(id);
      if (isSearchMode) {
        setBooks((prev) => prev.filter((b) => b.id !== id));
      } else {
        loadPage(page);
      }
    } catch (err) {
      setError(extractErrorMessage(err));
    }
  };

  return (
    <div className="book-list-page">
      <div className="page-header">
        <h1>Books</h1>
        <Link to="/books/new" className="btn btn-primary">
          + New Book
        </Link>
      </div>

      <form className="search-form" onSubmit={handleSearchSubmit}>
        <input
          type="text"
          placeholder="Search by title, author, or ISBN..."
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
        />
        <button type="submit" className="btn">
          Search
        </button>
        {isSearchMode && (
          <button
            type="button"
            className="btn btn-secondary"
            onClick={() => {
              setKeyword('');
              loadPage(0);
            }}
          >
            Clear
          </button>
        )}
      </form>

      <ErrorBanner message={error} onDismiss={() => setError('')} />

      {loading ? (
        <LoadingSpinner />
      ) : books.length === 0 ? (
        <p>No books found.</p>
      ) : (
        <table className="book-table">
          <thead>
            <tr>
              <th>Title</th>
              <th>ISBN</th>
              <th>Author</th>
              <th>Category</th>
              <th>Price</th>
              <th>Stock</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {books.map((book) => (
              <tr key={book.id}>
                <td>
                  <Link to={`/books/${book.id}`}>{book.title}</Link>
                </td>
                <td>{book.isbn}</td>
                <td>{book.authorName}</td>
                <td>{book.categoryName}</td>
                <td>${Number(book.price).toFixed(2)}</td>
                <td>{book.stockQuantity}</td>
                <td className="actions">
                  <Link to={`/books/${book.id}/edit`} className="btn btn-small">
                    Edit
                  </Link>
                  <button
                    type="button"
                    className="btn btn-small btn-danger"
                    onClick={() => handleDelete(book.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {!isSearchMode && totalPages > 1 && (
        <div className="pagination">
          <button type="button" disabled={page <= 0} onClick={() => loadPage(page - 1)}>
            Previous
          </button>
          <span>
            Page {page + 1} of {totalPages}
          </span>
          <button type="button" disabled={page >= totalPages - 1} onClick={() => loadPage(page + 1)}>
            Next
          </button>
        </div>
      )}
    </div>
  );
}

export default BookListPage;
