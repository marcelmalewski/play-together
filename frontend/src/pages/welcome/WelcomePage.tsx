import { Link } from "react-router-dom";

export function WelcomePage() {
  return (
    <div className="flex h-screen w-screen flex-col items-center justify-center">
      <p>welcome</p>
      <Link
        to="/login"
        className="hover:bg-cyan-700-20d inline-block flex h-12 w-40 items-center justify-center rounded-md bg-cyan-700 text-2xl"
      >
        go to login
      </Link>
      <Link
        to="/register"
        className="hover:bg-cyan-700-20d mt-10 inline-block flex h-12 w-40 items-center justify-center rounded-md bg-cyan-700 text-2xl"
      >
        go to register
      </Link>
    </div>
  );
}
