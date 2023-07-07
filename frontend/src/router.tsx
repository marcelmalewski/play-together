import { createBrowserRouter, Navigate } from "react-router-dom";
import { LoginPage } from "./pages/auth/LoginPage";
import { RegisterPage } from "./pages/auth/RegisterPage";
import { WelcomePage } from "./pages/welcome/WelcomePage";
import { MyProfilePage } from "./pages/gamer/MyProfilePage";
import { LoginGuard } from "./guards/LoginGuard";

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
  {
    path: "my-profile",
    element: (
      <LoginGuard>
        <MyProfilePage />
      </LoginGuard>
    ),
  },
]);
