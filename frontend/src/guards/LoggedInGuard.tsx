import React, { PropsWithChildren, ReactNode } from "react";
import { CircularProgress } from "@mui/material";
import { useGetMyDataQuery } from "../store/apiSlice";
import { Navigate } from "react-router-dom";
import { useSnackbarNotification } from "../hooks/sharedHooks";
import { ErrorResponse } from "../interfaces/sharedInterfaces";

export function LoggedInGuard({ children }: PropsWithChildren) {
  const { data: myData, isSuccess, isError, error } = useGetMyDataQuery();
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

  useSnackbarNotification(isError, error as ErrorResponse);

  return <>{content}</>;
}
