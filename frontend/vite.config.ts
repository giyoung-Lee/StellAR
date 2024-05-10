// vite.config.ts
import { defineConfig, loadEnv } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path';

export default defineConfig(({ mode }) => {
  
  // 환경 변수 로딩
  // EC2
  const env = loadEnv(mode, path.resolve('/'));

  return {
    plugins: [react()],
    define: {
      'process.env': {...env}
    }
  };

  // 로컬
  // const env = loadEnv(mode, process.cwd(), '');

  // return {
  //   plugins: [react()],
  //   define: {
  //     'process.env': env
  //   }
  // };
});