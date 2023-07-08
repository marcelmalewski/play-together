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

  content = children;

  // if (isSuccess) {
  //   console.log(myData);
  //   if (myData === null) {
  //     // content = <NotLoggedInErrorPage />;
  //     content = <div>not logged in page</div>;
  //   } else {
  //     content = children;
  //   }
  // } else if (isError) {
  //   content = <div>generic error page</div>;
  // }

  return <>{content}</>;
}
