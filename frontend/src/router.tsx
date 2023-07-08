import { createBrowserRouter, Navigate } from "react-router-dom";
import { LoginPage } from "./pages/auth/LoginPage";
import { RegisterPage } from "./pages/auth/RegisterPage";
import { WelcomePage } from "./pages/welcome/WelcomePage";
import { MyProfilePage } from "./pages/gamer/MyProfilePage";
import { CheckIfLoggedInGuard } from "./guards/CheckIfLoggedInGuard";
import { CheckIfNotLoggedInGuard } from "./guards/CheckIfNotLoggedInGuard";

export const router = createBrowserRouter([
  {
    path: "*",
    element: <Navigate replace to="/" />,
  },
  {
    path: "/",
    element: (
      <CheckIfNotLoggedInGuard>
        <WelcomePage />
      </CheckIfNotLoggedInGuard>
    ),
  },
  {
    path: "login",
    element: (
      <CheckIfNotLoggedInGuard>
        <LoginPage />
      </CheckIfNotLoggedInGuard>
    ),
  },
  {
    path: "register",
    element: (
      <CheckIfNotLoggedInGuard>
        <RegisterPage />
      </CheckIfNotLoggedInGuard>
    ),
  },
  {
    path: "my-profile",
    element: (
      <CheckIfLoggedInGuard>
        <MyProfilePage />
      </CheckIfLoggedInGuard>
    ),
  },
]);
