import { Link } from 'react-router-dom';

function Navbar() {
  return (
    <nav className="bg-gray-800 p-4">
      <div className="max-w-7xl mx-auto flex justify-between items-center">
        <Link to="/" className="text-white text-lg font-semibold hover:text-gray-400">
          HOME
        </Link>
        <Link to="/cart" className="text-white text-lg font-semibold hover:text-gray-400">
          CART
        </Link>
      </div>
    </nav>
  );
}

export default Navbar;
