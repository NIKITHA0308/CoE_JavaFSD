
import { useCart } from '../context/CartContext';

function Cart() {
  const { state, dispatch } = useCart();

  const removeFromCart = item => {
    dispatch({ type: 'REMOVE_FROM_CART', payload: item });
  };

  const totalPrice = state.cart.reduce((total, item) => total + item.price, 0);

  return (
    <div>
      {state.cart.map(item => (
        <div key={item.id}>
          <h2>{item.name}</h2>
          <p>{item.price}</p>
          <button onClick={() => removeFromCart(item)}>Remove</button>
        </div>
      ))}
      <h3>Total: {totalPrice}</h3>
    </div>
  );
}

export default Cart;
