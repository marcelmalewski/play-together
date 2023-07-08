import React, { PropsWithChildren, ReactNode } from "react";
import { CircularProgress } from "@mui/material";
import { useGetMyDataQuery } from "../store/apiSlice";
import { Navigate } from "react-router-dom";
import { useErrorSnackbarWithError } from "../hooks/sharedHooks";

export function CheckIfLoggedInGuard({ children }: PropsWithChildren) {
  const { isSuccess, isError, error } = useGetMyDataQuery();
  let content: ReactNode = (
    <div className="flex h-screen w-screen items-center justify-center bg-o-1">
      <CircularProgress size={90} />
    </div>
  );

  if (isSuccess) {
    content = children;
  } else if (isError) {
    if ("status" in error) {
      if (error.status === 401) {
        content = <Navigate replace to="/" />;
      }
    } else {
      content = <div>generic error page</div>;
    }
  }

  useErrorSnackbarWithError(isError, error);

  return <>{content}</>;
}
