import {createBrowserRouter} from "react-router-dom";
import {AuthLayout} from "./pages/layouts/AuthLayout";
import {Login} from "./pages/login/Login";
import {Signup} from "./pages/signup/Signup";

export const router = createBrowserRouter([
   {
      element: <AuthLayout />,
      children: [
         { path: "login", element: <Login /> },
         { path: "signup", element: <Signup /> },
      ],
   }
])
