import {
  apiSlice,
  useGetMyDataQuery,
  useLogoutMutation,
} from "../../store/apiSlice";
import { enqueueSnackbar } from "notistack";
import { useNavigate } from "react-router-dom";
import { useAppDispatch } from "../../store/storeHooks";
import { Slide } from "@mui/material";
import React from "react";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";

export function MyProfilePage() {
  const { data: myData, isSuccess, isError, error } = useGetMyDataQuery();
  const [logout] = useLogoutMutation();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();

  console.log(myData);
  const [open, setOpen] = React.useState(true);

  function tryToLogout() {
    logout()
      .unwrap()
      .catch((error) => {
        if (error.status === 401) {
          dispatch(apiSlice.util.resetApiState());
          enqueueSnackbar("Logged out successfully", {
            variant: "success",
          });
        } else {
          enqueueSnackbar(
            "Something went wrong on the server while trying to log out",
            {
              variant: "error",
            }
          );
        }
      });
  }

  function openNavbar() {
    setOpen(true);
  }

  function closeNavbar() {
    setOpen(false);
  }

  //TODO na refreshuy jest animacja poprawic to albo jakos inaczej zrobic animacje
  return (
    <div className="flex h-screen w-screen">
      <Slide direction="right" in={open} mountOnEnter unmountOnExit>
        <nav className="flex h-full flex-col border-r border-base-border bg-slate-800 p-3 text-lg">
          <div className="flex flex-grow flex-col items-start">
            <ArrowBackIosNewIcon
              type="button"
              onClick={closeNavbar}
              className="self-end hover:text-light-hov"
              sx={{
                fontSize: "1.5rem",
              }}
            />
            <button className="after:block after:h-0.5 after:w-0 after:bg-base-line after:duration-200 hover:after:w-full">
              My profile
            </button>
            <button className="after:block after:h-0.5 after:w-0 after:bg-base-line after:duration-200 hover:after:w-full">
              Session panel
            </button>
            <button className="after:block after:h-0.5 after:w-0 after:bg-base-line after:duration-200 hover:after:w-full">
              Admin panel
            </button>
          </div>
          <button
            onClick={tryToLogout}
            className="self-start after:block after:h-0.5 after:w-0 after:bg-base-line after:duration-200 hover:after:w-full"
          >
            Logout
          </button>
        </nav>
      </Slide>
      {!open && (
        <ArrowBackIosNewIcon
          type="button"
          onClick={openNavbar}
          className="m-3 rotate-180 self-start hover:text-light-hov"
          sx={{
            fontSize: "1.5rem",
          }}
        />
      )}
      <div className="flex flex-grow items-center justify-center gap-5 border">
        <p>my profile</p>
      </div>
    </div>
  );
}
