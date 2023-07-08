import { createBrowserRouter, Navigate } from "react-router-dom";
import { LoginPage } from "./pages/auth/LoginPage";
import { RegisterPage } from "./pages/auth/RegisterPage";
import { WelcomePage } from "./pages/welcome/WelcomePage";
import { MyProfilePage } from "./pages/gamer/MyProfilePage";
import { MoveIfNotLoggedInGuard } from "./guards/MoveIfNotLoggedInGuard";
import { MoveIfLoggedInGuard } from "./guards/MoveIfLoggedInGuard";

export const router = createBrowserRouter([
  {
    path: "*",
    element: <Navigate replace to="/" />,
  },
  {
    path: "/",
    element: (
      <MoveIfLoggedInGuard>
        <WelcomePage />
      </MoveIfLoggedInGuard>
    ),
  },
  {
    path: "login",
    element: (
      <MoveIfLoggedInGuard>
        <LoginPage />
      </MoveIfLoggedInGuard>
    ),
  },
  {
    path: "register",
    element: (
      <MoveIfLoggedInGuard>
        <RegisterPage />
      </MoveIfLoggedInGuard>
    ),
  },
  {
    path: "my-profile",
    element: (
      <MoveIfNotLoggedInGuard>
        <MyProfilePage />
      </MoveIfNotLoggedInGuard>
    ),
  },
]);
