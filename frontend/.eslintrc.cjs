module.exports = {
  root: true,
  env: { browser: true, es2020: true },
  extends: [
    'airbnb',
    'airbnb-typescript',
    'eslint:recommended',
    'plugin:@typescript-eslint/recommended',
    'plugin:react-hooks/recommended',
    'plugin:storybook/recommended',
    'plugin:prettier/recommended',
  ],
  ignorePatterns: ['dist', '.eslintrc.cjs', 'vite.config.ts'],
  parser: '@typescript-eslint/parser',
  plugins: ['react-refresh', 'react', '@typescript-eslint', 'prettier'],
  rules: {
    'prettier/prettier': ['error'],
    'react-refresh/only-export-components': [
      'warn',
      { allowConstantExport: true },
    ],
    'react/function-component-definition': [
      2,
      { namedComponents: ['arrow-function', 'function-declaration'] },
    ],
    'react/react-in-jsx-scope': 'off',
    'import/prefer-default-export': 'off',
    'import/no-extraneous-dependencies': 'off',
    'jsx-a11y/label-has-associated-control': 'off',
    'jsx-a11y/click-events-have-key-events': 'off',
    'jsx-a11y/no-noninteractive-element-interactions': 'off',
    'no-restricted-globals': 'off',
    'react/no-array-index-key': 'off',
    // 모든 문자열의 숫자를 16진수로 간주, 10진수 명시하지 않으면 경고 뜨는 룰 off
    radix: 'off',
    // this를 통해서만 클래스 내부 데이터에 접근하는 방식 off
    'class-methods-use-this': 'off',
    // 반복문 안에 await 사용하면 안되는 rule off
    'no-await-in-loop': 'off',
    'no-console': 'off',
    'no-plusplus': 'off',
    'no-continue': 'off',
    'no-useless-escape': 'off',
  },
};
