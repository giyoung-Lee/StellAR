import { Routes, Route } from 'react-router-dom';
import HomePage from '../pages/HomePage';
import EntryPage from '../pages/EntryPage';
import AuthPage from '../pages/AuthPage';
import MyStarPage from '../pages/MyStarPage';
import StarMarkPage from '../pages/StarMarkPage';
import EventPage from '../pages/EventPage';
import ShopPage from '../pages/ShopPage';
import OrderPage from '../pages/OrderPage';

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/entry" element={<EntryPage />} />
      <Route path="/auth" element={<AuthPage />} />
      <Route path="/myStar/:id" element={<MyStarPage />} />
      <Route path="/starMark/:id" element={<StarMarkPage />} />
      <Route path="/event" element={<EventPage />} />
      <Route path="/shop" element={<ShopPage />} />
      <Route path="/order/:id" element={<OrderPage />} />
    </Routes>
  );
};

export default AppRoutes;
