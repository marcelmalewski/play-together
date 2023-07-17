import React, { PropsWithChildren, ReactNode } from "react";
import { CircularProgress } from "@mui/material";
import { useGetMyDataQuery } from "../store/apiSlice";
import { Navigate } from "react-router-dom";
import { useErrorSnackbarWithMessage } from "../other/hooks";

export function MoveIfLoggedInGuard({ children }: PropsWithChildren) {
  const { isSuccess, isError } = useGetMyDataQuery();
  let content: ReactNode = (
    <div className="flex h-screen w-screen items-center justify-center bg-o-1">
      <CircularProgress size={90} />
    </div>
  );

  if (isSuccess) {
    console.log("zalogowany w home");
    content = <Navigate replace to="/my-profile" />;
  } else if (isError) {
    content = children;
    //TODO inne niz 401 to generic error
    // content = <GenericErrorPage error={error} />;
  }

  useErrorSnackbarWithMessage(isSuccess, "You can't enter, you are logged in");

  return <>{content}</>;
}
