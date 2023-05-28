import { Outlet, useLocation } from "react-router-dom";
import { FullScreenForm } from "../components/FullScreenForm";

export function FullScreenFormLayout() {
  const location = useLocation();
  const isLoginPage = location.pathname === "/login";

  return (
    <FullScreenForm>
      <FullScreenForm.Body>
        <Outlet />
      </FullScreenForm.Body>
      <FullScreenForm.BelowCard>
        <p>&copy; 2021. All rights reserved.</p>
      </FullScreenForm.BelowCard>
    </FullScreenForm>
  );
}
