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
        //hover -> 20l or 20d?
        //border -> 20l or 20d?
        //zmiana slate-500 na cos innego to po prostu ctrl+shift+r
        "base-bg": "#0f172a", //slate-900
        "base-text": "#cbd5e1", //slate-300
        "base-scroll": "#616470",//TODO do zmiany

        "form-bg": "#1e293b", //slate-800
        "form-border": "#475569", //slate-600
        "placeholder": "#9ca3af", //gray-400

        "base-button": "#0e7490", //cyan-700
        "base-button-hov": "#155e75", //cyan-800
        "error": "#ef4444", //red-500
      }
    },
  },
  plugins: [],
}
