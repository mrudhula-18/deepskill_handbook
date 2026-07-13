import { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { getBook, deleteBook } from '../services/bookApi.js';
import LoadingSpinner from '../components/LoadingSpinner.jsx';
import ErrorBanner from '../components/ErrorBanner.jsx';

function extractErrorMessage(err) {
  return err?.response?.data?.message || err?.message || 'Something went wrong.';
}

function BookDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [book, setBook] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    let isMounted = true;
    setLoading(true);
    setError('');
    getBook(id)
      .then((data) => {
        if (isMounted) {
          setBook(data);
        }
      })
      .catch((err) => {
        if (isMounted) {
          setError(extractErrorMessage(err));
        }
      })
      .finally(() => {
        if (isMounted) {
          setLoading(false);
        }
      });
    return () => {
      isMounted = false;
    };
  }, [id]);

  const handleDelete = async () => {
    if (!window.confirm('Delete this book?')) {
      return;
    }
    try {
      await deleteBook(id);
      navigate('/');
    } catch (err) {
      setError(extractErrorMessage(err));
    }
  };

  if (loading) {
    return <LoadingSpinner />;
  }

  return (
    <div className="book-detail-page">
      <ErrorBanner message={error} onDismiss={() => setError('')} />

      {book && (
        <>
          <h1>{book.title}</h1>
          <dl className="detail-list">
            <dt>ISBN</dt>
            <dd>{book.isbn}</dd>
            <dt>Author</dt>
            <dd>{book.authorName}</dd>
            <dt>Category</dt>
            <dd>{book.categoryName}</dd>
            <dt>Price</dt>
            <dd>${Number(book.price).toFixed(2)}</dd>
            <dt>Stock Quantity</dt>
            <dd>{book.stockQuantity}</dd>
          </dl>

          <div className="detail-actions">
            <Link to="/" className="btn btn-secondary">
              Back
            </Link>
            <Link to={`/books/${book.id}/edit`} className="btn btn-primary">
              Edit
            </Link>
            <button type="button" className="btn btn-danger" onClick={handleDelete}>
              Delete
            </button>
          </div>
        </>
      )}
    </div>
  );
}

export default BookDetailPage;
