import { Route, Routes, useLocation } from 'react-router-dom';

import NotFoundPage from '../pages/NotFoundPage';
import WebEntryPage from '../pages/WebPage';
import EmbPage from '../pages/EmbPage';

const WebRoutes = () => {
  return (
    <Routes>
      <Route path="*" element={<NotFoundPage />} />
      <Route path="/" element={<WebEntryPage />} />
      <Route path="/emb" element={<EmbPage />} />
    </Routes>
  );
};

export default WebRoutes;
