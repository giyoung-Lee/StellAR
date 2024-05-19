import { createGlobalStyle } from 'styled-components';
import Background from '/img/background.jpg';

const GlobalStyle = createGlobalStyle`
    html {
      --color-dark: #0A0515;
      --color-navy: #041840;
      --color-blue: #020D63;
      --color-orange: #EBB67B;
      --color-lightorange: #F2CEA2;
      --color-light: #F1F4F4;
    }

    body {
        /* background-color: var(--color-dark); */
        background-color: black;
        background-image: url(${Background});
        background-size: cover;

        -webkit-user-select:none;
        -moz-user-select:none;
        -ms-user-select:none;
        user-select:none;
    }

    /* div {
      margin: 0;
    } */
    p {
      margin: 0;
    }
`;

export default GlobalStyle;
