import { Routes, Route } from 'react-router-dom';
import NavBar from './components/NavBar.jsx';
import BookListPage from './pages/BookListPage.jsx';
import BookDetailPage from './pages/BookDetailPage.jsx';
import BookFormPage from './pages/BookFormPage.jsx';

function App() {
  return (
    <div className="app-shell">
      <NavBar />
      <main className="app-content">
        <Routes>
          <Route path="/" element={<BookListPage />} />
          <Route path="/books/new" element={<BookFormPage mode="create" />} />
          <Route path="/books/:id" element={<BookDetailPage />} />
          <Route path="/books/:id/edit" element={<BookFormPage mode="edit" />} />
          <Route path="*" element={<p>Page not found.</p>} />
        </Routes>
      </main>
    </div>
  );
}

export default App;
