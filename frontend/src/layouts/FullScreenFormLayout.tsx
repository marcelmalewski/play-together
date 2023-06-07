import { ReactNode } from "react";
import { Link, useLocation } from "react-router-dom";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";

interface FullScreenCardProps {
  children: ReactNode;
}

export function FullScreenFormLayout({ children }: FullScreenCardProps) {
  const location = useLocation();
  const isLoginPage = location.pathname === "/login";

  return (
    <div className="flex h-screen w-screen flex-col items-center justify-center gap-4">
      <nav className="absolute top-0 flex w-full items-center justify-start p-2">
        <ArrowBackIosNewIcon
          sx={{
            fontSize: "large",
          }}
        />
        <Link
          to="/yes"
          className="after:bg-o-5 text-lg after:block after:h-0.5 after:w-0 after:rounded-lg after:duration-200 hover:after:w-full"
        >
          Welcome page
        </Link>
      </nav>
      {children}
      <nav className="text-xl">Don't have an account? Register</nav>
    </div>
  );
}
