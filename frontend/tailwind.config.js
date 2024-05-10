/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}", "./index.html"],
  theme: {
    extend: {
      colors: {
        border: 'hsl(var(--border))',
        input: 'hsl(var(--input))',
        ring: 'hsl(var(--ring))',
        background: 'hsl(var(--background))',
        foreground: 'hsl(var(--foreground))',
        primary: {
          DEFAULT: 'hsl(var(--primary))',
          foreground: 'hsl(var(--primary-foreground))',
        },
        secondary: {
          DEFAULT: 'hsl(var(--secondary))',
          foreground: 'hsl(var(--secondary-foreground))',
        },
        destructive: {
          DEFAULT: 'hsl(var(--destructive))',
          foreground: 'hsl(var(--destructive-foreground))',
        },
        muted: {
          DEFAULT: 'hsl(var(--muted))',
          foreground: 'hsl(var(--muted-foreground))',
        },
        accent: {
          DEFAULT: 'hsl(var(--accent))',
          foreground: 'hsl(var(--accent-foreground))',
        },
        popover: {
          DEFAULT: 'hsl(var(--popover))',
          foreground: 'hsl(var(--popover-foreground))',
        },
        card: {
          DEFAULT: 'hsl(var(--card))',
          foreground: 'hsl(var(--card-foreground))',
        },
        dark: '#0A0515',
        navy: '#041840',
        blue: '#020D63',
        orange: '#EBB67B',
        lightorange: '#F2CEA2',
        light: '#F1F4F4',

        // 글래스모피즘 적용
        boxShadow: {
          'custom': '0 8px 32px 0 rgba(31, 38, 135, 0.37)'
        },
        backdropFilter: {
          'none': 'none',
          'sm': 'blur(4px)'
        },

          // 폼 요소의 기본 스타일 재정의
          formControl: {
            padding: '0', // padding을 0으로 설정
            margin: '0', // margin을 0으로 설정
            outline: 'none', // outline을 none으로 설정
          },
          boxShadow: {
            focus: 'none', // 포커스 시 그림자 없앰
          },
          ringWidth: {
            focus: '0', // 포커스 시 테두리 너비를 0으로 설정
          },
          ringColor: {
            focus: 'transparent', // 포커스 시 테두리 색상을 투명하게 설정
          },
          outline: {
            focus: 'none', // 포커스 시 외곽선 없앰
          },
          borderColor: {
            focus: 'transparent', // 포커스 시 테두리 색상을 투명하게 설정
          }
      },
      borderRadius: {
        lg: 'var(--radius)',
        md: 'calc(var(--radius) - 2px)',
        sm: 'calc(var(--radius) - 4px)',
      },
    },
  },
  plugins: [
    require('@tailwindcss/forms')({
      strategy: 'class', // 폼 요소 스타일링 전략을 클래스 기반으로 설정
    }),
    require('tailwindcss-filters'),
  ],
}