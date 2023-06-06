import { Link } from "react-router-dom";

export function WelcomePage() {
  return (
    <div className="flex h-screen w-screen flex-col items-center justify-center">
      <p>welcome</p>
      <Link
        to="/login"
        className="inline-block flex h-12 w-40 items-center justify-center rounded-md bg-base-button text-2xl duration-200 hover:bg-base-button-hov"
      >
        Login
      </Link>
      <Link
        to="/register"
        className="mt-10 inline-block flex h-12 w-40 items-center justify-center rounded-md bg-base-button text-2xl duration-200 hover:bg-base-button-hov"
      >
        Register
      </Link>
    </div>
  );
}
