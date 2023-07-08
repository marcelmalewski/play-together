import React, { useEffect } from "react";
import { enqueueSnackbar } from "notistack";
import { SerializedError } from "@reduxjs/toolkit";
import { FetchBaseQueryError } from "@reduxjs/toolkit/query";

//TODO dodac te sprawdzanie co to za error, ale to jakas funkcja bedzie robic
//TODO jak backend bedzie zwracac message: https://redux-toolkit.js.org/rtk-query/usage-with-typescript#inline-error-handling-example
export function useErrorSnackbarWithError(
  isError: boolean,
  error: FetchBaseQueryError | SerializedError | undefined
) {
  useEffect(() => {
    if (isError) {
      if ("status" in error!) {
        if (error.status === 401) {
          enqueueSnackbar("You can't enter, you are not logged in", {
            variant: "error",
          });
        }
      }
    }
  }, [isError]);
}

export function useErrorSnackbarWithMessage(
  condition: boolean,
  message: string
) {
  useEffect(() => {
    if (condition) {
      enqueueSnackbar(message, {
        variant: "error",
      });
    }
  }, [condition]);
}
