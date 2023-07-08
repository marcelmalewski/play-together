import React, { PropsWithChildren, ReactNode } from "react";
import { CircularProgress } from "@mui/material";
import { useGetMyDataQuery } from "../store/apiSlice";

export function LoggedInGuard({ children }: PropsWithChildren) {
  const { data: myData, isSuccess, isError, error } = useGetMyDataQuery();
  let content: ReactNode = (
    <div className="flex h-screen w-screen items-center justify-center bg-o-1">
      <CircularProgress size={90} />
    </div>
  );

  //TODO https://redux-toolkit.js.org/rtk-query/usage-with-typescript#inline-error-handling-example
  if (isSuccess) {
    content = children;
  } else if (isError) {
    if ("status" in error) {
      if (error.status === 401) {
        // content = <NotLoggedInErrorPage />;
        content = (
          <div className="flex items-center justify-center">
            not logged in page
          </div>
        );
      }
    } else {
      content = <div>generic error page</div>;
    }
  }

  return <>{content}</>;
}
