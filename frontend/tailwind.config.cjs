/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        sans: ["Lato", "sans-serif"],
      },
      colors: {
        'base-background': '#FBE8A6',
        'base-orange': '#F4976C',
        "o-7": "#616470",
      },
      boxShadow: {
        'card': '1px 1px 4px #bdbdbd, -1px -1px 4px #bdbdbd',
      }
    },
  },
  plugins: [],
}
