import { useCart } from '../context/CartContext';

function CartPage() {
  const { state, dispatch } = useCart();

  const removeFromCart = item => {
    dispatch({ type: 'REMOVE_FROM_CART', payload: item });
  };

  const totalPrice = state.cart.reduce((total, item) => total + item.price, 0);

  return (
    <div className="max-w-2xl mx-auto my-8">
      {state.cart.map(item => (
        <div key={item.id} className="flex justify-between items-center p-4 mb-4 border rounded shadow">
          <div>
            <h2 className="text-lg font-bold">{item.name}</h2>
            <p className="text-gray-600">₹{item.price}</p>
          </div>
          <button
            onClick={() => removeFromCart(item)}
            className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-700 transition-colors"
          >
            Remove
          </button>
        </div>
      ))}
      <div className="text-right">
        <h3 className="text-xl font-bold">Total: ₹{totalPrice}</h3>
      </div>
    </div>
  );
}

export default CartPage;
