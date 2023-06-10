import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import { RouterProvider } from "react-router-dom";
import { router } from "./router";
import { store } from "./store/store";
import { Provider } from "react-redux";
import { MaterialDesignContent, SnackbarProvider } from "notistack";
import { styled } from "@mui/material";
import ErrorOutlineIcon from "@mui/icons-material/ErrorOutline";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";

const StyledMaterialDesignContent = styled(MaterialDesignContent)(() => ({
  "&.notistack-MuiContent-success": {
    backgroundColor: "#2D7738",
  },
  "&.notistack-MuiContent-error": {
    backgroundColor: "#970C0C",
  },
}));

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <React.StrictMode>
    <Provider store={store}>
      <SnackbarProvider
        maxSnack={5}
        anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
        iconVariant={{
          success: <CheckCircleIcon className="mr-3" />,
          error: <ErrorOutlineIcon className="mr-3" />,
        }}
        Components={{
          success: StyledMaterialDesignContent,
          error: StyledMaterialDesignContent,
        }}
      >
        <RouterProvider router={router} />
      </SnackbarProvider>
    </Provider>
  </React.StrictMode>
);
