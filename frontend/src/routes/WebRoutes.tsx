import { Route, Routes, useLocation } from 'react-router-dom';

import NotFoundPage from '../pages/NotFoundPage';
import WebEntryPage from '../pages/WebPage';

const WebRoutes = () => {
  return (
    <Routes>
      <Route path="*" element={<NotFoundPage />} />
      <Route path="/" element={<WebEntryPage />} />
    </Routes>
  );
};

export default WebRoutes;

