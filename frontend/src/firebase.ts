// src/firebase.ts
import { initializeApp } from 'firebase/app';
import { getMessaging, getToken, onMessage, Messaging } from 'firebase/messaging';
import { useState } from 'react';

const firebaseConfig = {
  apiKey: import.meta.env.VITE_REACT_APP_FIREBASE_API_KEY,
  authDomain: import.meta.env.VITE_REACT_APP_FIREBASE_AUTH_DOMAIN,
  projectId: import.meta.env.VITE_REACT_APP_FIREBASE_PROJECT_ID,
  storageBucket: import.meta.env.VITE_REACT_APP_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: import.meta.env.VITE_REACT_APP_FIREBASE_MESSAGING_SENDER_ID,
  appId: import.meta.env.VITE_REACT_APP_FIREBASE_APP_ID,
  measurementId: import.meta.env.VITE_REACT_APP_FIREBASE_MEASUREMENT_ID
};

// const firebaseConfig = {
//   apiKey: "AIzaSyDO-oXXwpueTV4p2LF_lK6UJFeWXXQf6_E",
//   authDomain: "stellar-e2012.firebaseapp.com",
//   projectId: "stellar-e2012",
//   storageBucket: "stellar-e2012.appspot.com",
//   messagingSenderId: "400069443108",
//   appId: "1:400069443108:web:11fe7fac82f9b9e78862b1",
//   measurementId: "G-MWYW1NJBNG"
// };

// Firebase 앱 초기화
const app = initializeApp(firebaseConfig);

// Messaging 인스턴스 가져오기
const messaging: Messaging = getMessaging(app);

// 서비스 워커 등록
if ('serviceWorker' in navigator) {
  navigator.serviceWorker
    .register('/firebase-messaging-sw.js')
    .then((registration) => {
      console.log('Service Worker 등록 성공:', registration);
    })
    .catch((err) => {
      console.error('Service Worker 등록 실패:', err);
    });
}

export const requestPermission = async () => {
// export async function requestPermission() {
  const permission = await Notification.requestPermission();

  if (permission === 'granted') {
    try {
      const token = await getToken(messaging, { vapidKey: import.meta.env.VITE_REACT_APP_FIREBASE_VAPID_KEY });
      if (token) {
        console.log(`푸시 토큰 발급 완료: ${token}`);
        // 서버로 토큰 전송 로직 추가
      } else {
        console.log('푸시 토큰이 생성되지 않았습니다.');
      }
    } catch (error) {
      console.error('푸시 토큰 가져오는 중에 에러 발생:', error);
    }
  } else {
    console.log('푸시 권한 거부됨');
  }
}

// 메시지 수신 처리
onMessage(messaging, (payload) => {
  console.log('메시지 수신:', payload);
});