import { createBrowserRouter, Navigate } from "react-router-dom";
import { LoginPage } from "./pages/login/LoginPage";
import { RegisterPage } from "./pages/register/RegisterPage";
import { WelcomePage } from "./pages/welcome/WelcomePage";
import { MyProfilePage } from "./pages/gamer/MyProfilePage";

export const router = createBrowserRouter([
  {
    path: "*",
    element: <Navigate replace to="/" />,
  },
  {
    path: "/",
    element: <WelcomePage />,
  },
  { path: "login", element: <LoginPage /> },
  { path: "register", element: <RegisterPage /> },
  { path: "my-profile", element: <MyProfilePage /> },
]);
