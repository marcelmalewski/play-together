import {
  apiSlice,
  useGetMyDataQuery,
  useLogoutMutation,
} from "../../store/apiSlice";
import { enqueueSnackbar } from "notistack";
import { useNavigate } from "react-router-dom";
import { useAppDispatch } from "../../store/storeHooks";

export function MyProfilePage() {
  const { data: myData, isSuccess, isError, error } = useGetMyDataQuery();
  const [logout] = useLogoutMutation();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();

  console.log(myData);

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

  return (
    <div className="flex h-screen w-screen">
      <nav className="flex h-full flex-col border p-2 text-lg">
        <div className="flex flex-grow flex-col items-start">
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
      <div className="flex flex-grow items-center justify-center gap-5 border">
        <p>my profile</p>
      </div>
    </div>
  );
}
