// public/firebase-messaging-sw.js
importScripts('https://www.gstatic.com/firebasejs/9.2.0/firebase-app-compat.js');
importScripts('https://www.gstatic.com/firebasejs/9.2.0/firebase-messaging-compat.js');

const firebaseConfig = {
  apiKey: 'AIzaSyDO-oXXwpueTV4p2LF_lK6UJFeWXXQf6_E',
  authDomain: 'stellar-e2012.firebaseapp.com',
  projectId: 'stellar-e2012',
  storageBucket: 'stellar-e2012.appspot.com',
  messagingSenderId: '400069443108',
  appId: '1:400069443108:web:11fe7fac82f9b9e78862b1',
  measurementId: 'G-MWYW1NJBNG'
};

// Firebase 앱 초기화
firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();

messaging.onBackgroundMessage((payload) => {
  console.log('Received background message:', payload);
  const notificationTitle = payload.notification?.title || 'Background Message Title';
  const notificationOptions = {
    body: payload.notification?.body || 'Background Message Body',
    icon: '/firebase-logo.png'
  };

  self.registration.showNotification(notificationTitle, notificationOptions);
});
