import { createBrowserRouter, Navigate } from "react-router-dom";
import { FullScreenFormLayout } from "./layouts/FullScreenFormLayout";
import { LoginPage } from "./pages/loginPage/LoginPage";
import { RegisterPage } from "./pages/registerPage/RegisterPage";
import { WelcomePage } from "./pages/welcomePage/WelcomePage";

export const router = createBrowserRouter([
  {
    path: "*",
    element: <Navigate replace to="/" />,
  },
  {
    path: "/",
    element: <WelcomePage />,
  },
  {
    element: <FullScreenFormLayout />,
    children: [
      { path: "login", element: <LoginPage /> },
      { path: "register", element: <RegisterPage /> },
    ],
  },
]);
