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
      <Link
        to="/welcome"
        className="absolute left-0 top-0 w-fit p-3 after:block after:h-0.5 after:w-0 after:bg-base-line after:duration-200 hover:after:w-full"
      >
        <div className="flex items-center justify-center gap-2">
          <ArrowBackIosNewIcon
            sx={{
              fontSize: "large",
            }}
          />
          <p className="text-lg">Welcome page</p>
        </div>
      </Link>
      {children}
      {isLoginPage ? (
        <Link
          to="/register"
          className="text-xl after:block after:h-0.5 after:w-0 after:bg-base-line after:duration-200 hover:after:w-full"
        >
          Or create account
        </Link>
      ) : (
        <Link
          to="/login"
          className="text-xl after:block after:h-0.5 after:w-0 after:bg-base-line after:duration-200 hover:after:w-full"
        >
          Or login
        </Link>
      )}
    </div>
  );
}
