import { ReactNode } from "react";
import { Link, useLocation } from "react-router-dom";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";

interface FullScreenCardProps {
  children: ReactNode;
}

export function FullScreenFormLayout({ children }: FullScreenCardProps) {
  const location = useLocation();
  const isLoginPage = location.pathname === "/login";

  //TODO czy welcome page tez na dół dac?
  return (
    <div className="flex h-screen w-screen flex-col items-center justify-center gap-4">
      {/*<nav className="absolute top-0 flex w-full items-center justify-start gap-2 p-2">*/}
      {/*  <ArrowBackIosNewIcon*/}
      {/*    sx={{*/}
      {/*      fontSize: "large",*/}
      {/*    }}*/}
      {/*  />*/}
      {/*  <Link*/}
      {/*    to="/yes"*/}
      {/*    className="text-lg after:block after:h-0.5 after:w-0 after:bg-base-line after:duration-200 hover:after:w-full"*/}
      {/*  >*/}
      {/*    Welcome page*/}
      {/*  </Link>*/}
      {/*</nav>*/}
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
      <Link
        to="/register"
        className="text-xl after:block after:h-0.5 after:w-0 after:bg-base-line after:duration-200 hover:after:w-full"
      >
        Create account
      </Link>
    </div>
  );
}
