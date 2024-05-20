import { initializeApp } from 'firebase/app';
import { getMessaging, getToken, onMessage } from 'firebase/messaging';

const firebaseConfig = {
  apiKey: "AIzaSyDO-oXXwpueTV4p2LF_lK6UJFeWXXQf6_E",
  authDomain: "stellar-e2012.firebaseapp.com",
  projectId: "stellar-e2012",
  storageBucket: "stellar-e2012.appspot.com",
  messagingSenderId: "400069443108",
  appId: "1:400069443108:web:11fe7fac82f9b9e78862b1",
  measurementId: "G-MWYW1NJBNG"
};

// Firebase 앱 초기화
const app = initializeApp(firebaseConfig);

// Messaging 인스턴스 가져오기
const messaging = getMessaging(app);

export const requestPermission = async () => {
  const permission = await Notification.requestPermission();
  if (permission === 'granted') {
    try {
      const registration = await navigator.serviceWorker.ready;
      const token = await getToken(messaging, {
        vapidKey: "BPxdfbo29YzD9IS9wcXcKL0-b2zjOQCyqZIWLJiFWrkXPxkD2qM_2ROFkHc_tQOnxWKwOQXaYzzU_heXZ6cyuPk",
        serviceWorkerRegistration: registration
      });
      if (token) {
        // console.log(`푸시 토큰 발급 완료: ${token}`);
        return token;
      } else {
        console.log('푸시 토큰이 생성되지 않았습니다.');
      }
    } catch (error) {
      console.error('푸시 토큰 가져오는 중에 에러 발생:', error);
    }
  } else {
    console.log('푸시 권한 거부됨');
  }
  return null;
}

// 메시지 수신 처리
onMessage(messaging, (payload) => {
  console.log('메시지 수신:', payload);
  const notificationTitle = payload.notification?.title || 'Foreground Message Title';
  const notificationOptions = {
    body: payload.notification?.body || 'Foreground Message Body',
    icon: '/firebase-logo.png'
  };

  if (Notification.permission === 'granted') {
    new Notification(notificationTitle, notificationOptions);
  }
});
