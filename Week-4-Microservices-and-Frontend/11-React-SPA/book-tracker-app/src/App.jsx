import { useState } from 'react'
import BookForm from './components/BookForm.jsx'
import BookList from './components/BookList.jsx'
import ConditionalMessage from './components/ConditionalMessage.jsx'
import CounterDemo from './components/CounterDemo.jsx'

/**
 * Top-level layout. Owns:
 *  - `books`: the last list BookList fetched, kept here purely so
 *    ConditionalMessage can render a live count without re-fetching itself.
 *  - `refreshKey`: bumped after a successful add so BookList's effect
 *    re-runs and pulls the fresh list from the API.
 */
function App() {
  const [books, setBooks] = useState([])
  const [refreshKey, setRefreshKey] = useState(0)

  const handleBookAdded = () => {
    setRefreshKey((key) => key + 1)
  }

  return (
    <div className="app">
      <header className="app-header">
        <h1>Book Tracker</h1>
        <p>Week 4 React SPA - consumes the Week 3 Module 9 Books REST API.</p>
      </header>

      <main>
        <section className="panel">
          <h2>Add a Book</h2>
          <BookForm onBookAdded={handleBookAdded} />
        </section>

        <section className="panel">
          <h2>Library</h2>
          <ConditionalMessage count={books.length} />
          <BookList refreshKey={refreshKey} onBooksLoaded={setBooks} />
        </section>

        <section className="panel">
          <h2>Class vs. Function Components</h2>
          <CounterDemo />
        </section>
      </main>
    </div>
  )
}

export default App
