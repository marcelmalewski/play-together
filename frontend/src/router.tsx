import { createBrowserRouter, Navigate } from "react-router-dom";
import { LoginPage } from "./pages/auth/LoginPage";
import { RegisterPage } from "./pages/auth/RegisterPage";
import { WelcomePage } from "./pages/welcome/WelcomePage";
import { MyProfilePage } from "./pages/gamer/MyProfilePage";
import { LoggedInGuard } from "./guards/LoggedInGuard";
import { NotLoggedInGuard } from "./guards/NotLoggedInGuard";

export const router = createBrowserRouter([
  {
    path: "*",
    element: <Navigate replace to="/" />,
  },
  {
    path: "/",
    element: (
      <NotLoggedInGuard>
        <WelcomePage />
      </NotLoggedInGuard>
    ),
  },
  {
    path: "login",
    element: (
      <NotLoggedInGuard>
        <LoginPage />
      </NotLoggedInGuard>
    ),
  },
  {
    path: "register",
    element: (
      <NotLoggedInGuard>
        <RegisterPage />
      </NotLoggedInGuard>
    ),
  },
  {
    path: "my-profile",
    element: (
      <LoggedInGuard>
        <MyProfilePage />
      </LoggedInGuard>
    ),
  },
]);
