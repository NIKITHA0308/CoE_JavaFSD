import ProductCard from '../components/ProductCard';

const products = [
  { id: 1, name: 'CAR TOY', price: 100 },
  { id: 2, name: 'DRESS', price: 200 },
];

function Home() {
  return (
    <div className="max-w-7xl mx-auto my-8 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      {products.map(product => (
        <ProductCard key={product.id} product={product} />
      ))}
    </div>
  );
}

export default Home;
