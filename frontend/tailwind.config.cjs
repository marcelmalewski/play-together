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
        //hover -> 20l or 20d
        //border -> 20l or 20d
        //zmiana slate-500 na cos innego to po prostu ctrl+shift+r
        "o-1": "#616470",
        "cyan-700-10d": "#0b5c73",
      },
      boxShadow: {
        'card': '1px 1px 4px #bdbdbd, -1px -1px 4px #bdbdbd',
      }
    },
  },
  plugins: [],
}
