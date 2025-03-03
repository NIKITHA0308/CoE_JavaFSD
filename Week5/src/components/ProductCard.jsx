import { useCart } from '../context/CartContext';

function ProductCard({ product }) {
  const { dispatch } = useCart();

  const addToCart = () => {
    dispatch({ type: 'ADD_TO_CART', payload: product });
  };

  return (
    <div className="p-4 border rounded shadow hover:shadow-lg transition-shadow">
      <h2 className="text-lg font-bold">{product.name}</h2>
      <p className="text-gray-600">₹{product.price}</p>
      <button
        onClick={addToCart}
        className="mt-2 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-700 transition-colors"
      >
        Add to Cart
      </button>
    </div>
  );
}

export default ProductCard;
