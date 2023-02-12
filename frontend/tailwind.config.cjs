/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'base-background': '#FBE8A6',
        'base-orange': '#F4976C'
      },
      boxShadow: {
        'card': '0 4px 8px 0 rgba(0, 0, 0, 0.2)',
      }
    },
  },
  plugins: [],
}
