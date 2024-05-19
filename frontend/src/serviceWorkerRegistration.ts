const isLocalhost = Boolean(
  window.location.hostname === 'localhost' ||
  window.location.hostname === '[::1]' ||
  window.location.hostname.match(
    /^127(?:\.(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])){3}$/
  )
);

const PUBLIC_URL = "https://k10c105.p.ssafy.io";
const LOCAL_URL = "http://localhost:5173";

export const register = () => {
  if ('serviceWorker' in navigator) {
    return new Promise<void>((resolve, reject) => {
      window.addEventListener('load', () => {
        const swUrl = isLocalhost ? `${LOCAL_URL}/firebase-messaging-sw.js` : `${PUBLIC_URL}/firebase-messaging-sw.js`;

        navigator.serviceWorker.getRegistrations().then((registrations) => {
          registrations.forEach((registration) => {
            if (registration.scope !== swUrl) {
              registration.unregister();
              console.log(`Unregistered an old service worker with scope: ${registration.scope}`);
            }
          });

          navigator.serviceWorker.register(swUrl)
            .then((registration) => {
              // console.log('서비스 워커 등록 완료(SWR.ts):', registration.scope);
              return navigator.serviceWorker.ready;
            })
            .then(() => {
              console.log('서비스 워커가 활성화되었습니다.');
              resolve();
            })
            .catch((error) => {
              console.error('서비스 워커 등록 실패(SWR.ts):', error);
              reject(error);
            });
        });
      });
    });
  } else {
    return Promise.reject(new Error("서비스 워커를 지원하지 않는 브라우저입니다."));
  }
};
