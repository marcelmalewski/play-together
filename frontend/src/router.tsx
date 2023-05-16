import { createBrowserRouter } from "react-router-dom";
import { FormLayout } from "./layouts/FormLayout";
import { LoginPage } from "./pages/loginPage/LoginPage";
import { RegisterPage } from "./pages/registerPage/RegisterPage";

export const router = createBrowserRouter([
  {
    element: <FormLayout />,
    children: [
      { path: "login", element: <LoginPage /> },
      { path: "register", element: <RegisterPage /> },
    ],
  },
]);
