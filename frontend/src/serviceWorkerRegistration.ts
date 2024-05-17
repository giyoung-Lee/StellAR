// src/serviceWorkerRegistration.ts
const isLocalhost = Boolean(
  window.location.hostname === 'localhost' ||
  window.location.hostname === '[::1]' ||
  window.location.hostname.match(
    /^127(?:\.(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])){3}$/
  )
);

const isDistributed = Boolean(
  window.location.hostname === 'k10c105.p.ssafy.io' ||
  window.location.hostname === '[::1]' ||
  window.location.hostname.match(
    /^127(?:\.(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])){3}$/
  )
);

// EC2
const PUBLIC_URL = "https://k10c105.p.ssafy.io"

// 로컬
const LOCAL_URL = "http://localhost:5173"

export const register = () => {

  if ('serviceWorker' in navigator) {
    const publicUrl = new URL(PUBLIC_URL, window.location.href);
    const localUrl = new URL(LOCAL_URL, window.location.href);
    if (publicUrl.origin !== window.location.origin && localUrl.origin !== window.location.origin) {
      return;
    }
    
    window.addEventListener('load', () => {
      const swUrl = isLocalhost ? `${LOCAL_URL}/firebase-messaging-sw.js` : `${PUBLIC_URL}/firebase-messaging-sw.js`;

      if (isLocalhost || isDistributed) {
        checkValidServiceWorker(swUrl);
      }
    });
  }
}

export const registerValidSW = (swUrl: string) => {
  navigator.serviceWorker
    .register(swUrl)
    .then((registration) => {
      console.log('서비스 워커 등록 완료(SWR.ts):', registration.scope);
    })
    .catch((error) => {
      console.error('서비스 워커 등록 실패(SWR.ts):', error);
    });
}

export const checkValidServiceWorker = (swUrl: string) => {
  fetch(swUrl, { headers: { 'Service-Worker': 'script' } })
    .then((response) => {
      if (response.status === 404) {
        navigator.serviceWorker.ready.then((registration) => {
          registration.unregister().then(() => {
            window.location.reload();
          });
        });
      } else {
        registerValidSW(swUrl);
      }
    })
    .catch(() => {
      console.log('인터넷 연결이 없습니다. 앱이 오프라인 모드로 실행 중입니다.');
    });
}

export const unregister = () => {
  if ('serviceWorker' in navigator) {
    navigator.serviceWorker.ready
      .then((registration) => {
        registration.unregister();
      })
      .catch((error) => {
        console.error(error.message);
      });
  }
}


