import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import './index.css';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { register as registerServiceWorker } from './serviceWorkerRegistration';

const queryClient = new QueryClient();

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <App />
    </QueryClientProvider>
  </React.StrictMode>
);

// 서비스 워커 등록 후 푸시 알림 권한 요청
registerServiceWorker().then(() => {
  import('./firebase').then(({ requestPermission }) => requestPermission());
});
