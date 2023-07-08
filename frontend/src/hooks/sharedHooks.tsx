import React, { useEffect } from "react";
import { ErrorResponse } from "../interfaces/sharedInterfaces";
import { enqueueSnackbar } from "notistack";

//TODO dodac te sprawdzanie co to za error, ale to jakas funkcja bedzie robic
export function useSnackbarNotification(
  isError: boolean,
  errorResponse: ErrorResponse
) {
  useEffect(() => {
    if ("status" in errorResponse) {
      if (errorResponse.status === 401) {
      }
      enqueueSnackbar("You are not logged in", {
        variant: "error",
      });
    }
  }, []);
}
