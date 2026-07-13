import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
  getBook,
  createBook,
  updateBook,
  listAuthors,
  listCategories,
} from '../services/bookApi.js';
import LoadingSpinner from '../components/LoadingSpinner.jsx';
import ErrorBanner from '../components/ErrorBanner.jsx';

const EMPTY_FORM = {
  title: '',
  isbn: '',
  price: '',
  stockQuantity: '',
  authorId: '',
  categoryId: '',
};

function extractErrorMessage(err) {
  return err?.response?.data?.message || err?.message || 'Something went wrong.';
}

/**
 * Handles both "create" and "edit" modes.
 * - mode="create" is used for the /books/new route.
 * - mode="edit" is used for the /books/:id/edit route; the presence of an :id param
 *   also implies edit mode as a fallback if the prop is omitted.
 */
function BookFormPage({ mode }) {
  const { id } = useParams();
  const navigate = useNavigate();
  const isEditMode = mode === 'edit' || Boolean(id);

  const [form, setForm] = useState(EMPTY_FORM);
  const [authors, setAuthors] = useState([]);
  const [categories, setCategories] = useState([]);
  const [fieldErrors, setFieldErrors] = useState({});
  const [serverError, setServerError] = useState('');
  const [loading, setLoading] = useState(isEditMode);
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    // Load reference data (for the author/category dropdowns) and, in edit mode, the
    // existing book in parallel. BookResponseDto only exposes authorName/categoryName
    // (not ids), so once both are in we resolve the matching id by name.
    let isMounted = true;

    const requests = [listAuthors(), listCategories()];
    if (isEditMode) {
      requests.push(getBook(id));
    }

    Promise.all(requests)
      .then(([authorList, categoryList, book]) => {
        if (!isMounted) return;
        setAuthors(authorList || []);
        setCategories(categoryList || []);

        if (book) {
          const matchedAuthor = (authorList || []).find((a) => a.name === book.authorName);
          const matchedCategory = (categoryList || []).find((c) => c.name === book.categoryName);
          setForm({
            title: book.title ?? '',
            isbn: book.isbn ?? '',
            price: book.price ?? '',
            stockQuantity: book.stockQuantity ?? '',
            authorId: matchedAuthor ? matchedAuthor.id : '',
            categoryId: matchedCategory ? matchedCategory.id : '',
          });
        }
      })
      .catch((err) => {
        if (isMounted) {
          setServerError(extractErrorMessage(err));
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
  }, [id, isEditMode]);

  const handleChange = (field) => (event) => {
    setForm((prev) => ({ ...prev, [field]: event.target.value }));
  };

  const validate = () => {
    const errors = {};
    if (!form.title.trim()) {
      errors.title = 'Title must not be blank';
    }
    if (!form.isbn.trim()) {
      errors.isbn = 'ISBN must not be blank';
    }
    const price = Number(form.price);
    if (!form.price || Number.isNaN(price) || price <= 0) {
      errors.price = 'Price must be greater than zero';
    }
    const stock = Number(form.stockQuantity);
    if (form.stockQuantity === '' || Number.isNaN(stock) || stock < 0) {
      errors.stockQuantity = 'Stock quantity must be zero or greater';
    }
    if (!form.authorId) {
      errors.authorId = 'Author is required';
    }
    if (!form.categoryId) {
      errors.categoryId = 'Category is required';
    }
    return errors;
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    const errors = validate();
    setFieldErrors(errors);
    if (Object.keys(errors).length > 0) {
      return;
    }

    const dto = {
      title: form.title.trim(),
      isbn: form.isbn.trim(),
      price: Number(form.price),
      stockQuantity: Number(form.stockQuantity),
      authorId: Number(form.authorId),
      categoryId: Number(form.categoryId),
    };

    setSubmitting(true);
    setServerError('');
    try {
      if (isEditMode) {
        await updateBook(id, dto);
      } else {
        await createBook(dto);
      }
      navigate('/');
    } catch (err) {
      setServerError(extractErrorMessage(err));
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) {
    return <LoadingSpinner />;
  }

  return (
    <div className="book-form-page">
      <h1>{isEditMode ? 'Edit Book' : 'New Book'}</h1>

      <ErrorBanner message={serverError} onDismiss={() => setServerError('')} />

      <form onSubmit={handleSubmit} className="book-form">
        <div className="form-field">
          <label htmlFor="title">Title</label>
          <input id="title" type="text" value={form.title} onChange={handleChange('title')} />
          {fieldErrors.title && <span className="field-error">{fieldErrors.title}</span>}
        </div>

        <div className="form-field">
          <label htmlFor="isbn">ISBN</label>
          <input id="isbn" type="text" value={form.isbn} onChange={handleChange('isbn')} />
          {fieldErrors.isbn && <span className="field-error">{fieldErrors.isbn}</span>}
        </div>

        <div className="form-field">
          <label htmlFor="price">Price</label>
          <input id="price" type="number" step="0.01" min="0.01" value={form.price} onChange={handleChange('price')} />
          {fieldErrors.price && <span className="field-error">{fieldErrors.price}</span>}
        </div>

        <div className="form-field">
          <label htmlFor="stockQuantity">Stock Quantity</label>
          <input
            id="stockQuantity"
            type="number"
            step="1"
            min="0"
            value={form.stockQuantity}
            onChange={handleChange('stockQuantity')}
          />
          {fieldErrors.stockQuantity && <span className="field-error">{fieldErrors.stockQuantity}</span>}
        </div>

        <div className="form-field">
          <label htmlFor="authorId">Author</label>
          <select id="authorId" value={form.authorId} onChange={handleChange('authorId')}>
            <option value="">-- select an author --</option>
            {authors.map((author) => (
              <option key={author.id} value={author.id}>
                {author.name}
              </option>
            ))}
          </select>
          {fieldErrors.authorId && <span className="field-error">{fieldErrors.authorId}</span>}
        </div>

        <div className="form-field">
          <label htmlFor="categoryId">Category</label>
          <select id="categoryId" value={form.categoryId} onChange={handleChange('categoryId')}>
            <option value="">-- select a category --</option>
            {categories.map((category) => (
              <option key={category.id} value={category.id}>
                {category.name}
              </option>
            ))}
          </select>
          {fieldErrors.categoryId && <span className="field-error">{fieldErrors.categoryId}</span>}
        </div>

        <div className="form-actions">
          <button type="submit" className="btn btn-primary" disabled={submitting}>
            {submitting ? 'Saving...' : isEditMode ? 'Save Changes' : 'Create Book'}
          </button>
          <button type="button" className="btn btn-secondary" onClick={() => navigate('/')}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}

export default BookFormPage;
