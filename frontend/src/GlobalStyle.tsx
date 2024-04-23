import { createGlobalStyle } from 'styled-components';

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
        background-color: var(--color-dark);
    }

    div {
      margin: 0;
    }
    p {
      margin: 0;
    }
`;

export default GlobalStyle;
