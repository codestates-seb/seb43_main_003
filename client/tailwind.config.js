/** @type {import('tailwindcss').Config} */

export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      margin: {
        'main-top': '96px',
      },
      padding: {
        'main-top': '96px',
      },
      colors: {
        white1: '#FBF9F8',
        black1: '#OAOAOA',
        black2: '#111111',
        black3: '#222222',
        black4: '#4B4B4B',
        gray1: '#ECE9E7',
        gray2: '#DADADA',
        gray3: '#DCD9D6',
        gray4: '#B1B1B1',
        gray5: '#555555',
        gray6: '#595959',
        gray7: '#777777',
        gray8: '#787878',
        gray9: '#7A7A7A',
        gray10: '#858585',
        gray11: '#717171',
        gray12: '#C6C6C6',
        activeBlue: '#0045F6',
        kakaoYellow: '#FDDC3F',
      },
      fontFamily: {
        'noto-kr': ['Noto Sans KR', 'sans-serif'],
        play: ['Play', 'sans-serif'],
      },
      maxWidth: {
        limit: '1080px',
      },
      boxShadow: {
        around: 'rgba(0, 0, 0, 0.05) 0px 3px 50px',
      },
      keyframes: {
        'fadeIn-up': {
          '0%': {
            opacity: '0',
            transform: 'translateY(10%)',
          },
          '100%': {
            opacity: '1',
            transform: 'translateY(0)',
          },
        },
        'fadeIn-right': {
          '0%': {
            opacity: '0',
            transform: 'translateX(-20%)',
          },
          '100%': {
            opacity: '1',
            transform: 'translateX(0)',
          },
        },
      },
      animation: {
        'fadeIn-up': 'fadeIn-up 1s ease-in',
        'fadeIn-right': 'fadeIn-right 1s ease-in-out',
      },
    },
  },
  plugins: [],
};

module.exports = {
  ...module.exports,
  mode: 'jit',
  purge: ['./src/**/*.{js,jsx,ts,tsx}', './public/index.html'],
  darkMode: false,
  theme: {
    extend: {},
  },
  variants: {
    extend: {},
  },
  plugins: [],
};
