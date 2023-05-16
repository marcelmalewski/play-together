import {createBrowserRouter} from "react-router-dom";
import {AuthLayout} from "./layouts/AuthLayout";
import {Login} from "./pages/login/Login";
import {Register} from "./pages/register/Register";

export const router = createBrowserRouter([
   {
      element: <AuthLayout />,
      children: [
         { path: "login", element: <Login /> },
         { path: "signup", element: <Register /> },
      ],
   }
])
