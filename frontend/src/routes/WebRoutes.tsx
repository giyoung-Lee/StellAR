import { Route, Routes } from 'react-router-dom';

import NotFoundPage from '../pages/NotFoundPage';
import WebEntryPage from '../pages/WebPage';
import EmbPage from '../pages/EmbPage';
import HomePage from '../pages/HomePage';

const WebRoutes = () => {
  return (
    <Routes>
      <Route path="*" element={<NotFoundPage />} />
      <Route path="/" element={<WebEntryPage />} />
      <Route path="/entry" element={<WebEntryPage />} />
      <Route path="/emb" element={<EmbPage />} />
      <Route path="/preview" element={<HomePage />} />
    </Routes>
  );
};

export default WebRoutes;

