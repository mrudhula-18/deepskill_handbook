import { Link } from 'react-router-dom';

function NavBar() {
  return (
    <header className="navbar">
      <Link to="/" className="navbar-brand">
        DN5.0 Capstone Bookstore
      </Link>
      <nav className="navbar-links">
        <Link to="/">Books</Link>
        <Link to="/books/new">New Book</Link>
      </nav>
    </header>
  );
}

export default NavBar;
