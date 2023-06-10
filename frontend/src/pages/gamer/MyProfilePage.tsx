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
    <div className="flex flex-col gap-6">
      yes
      <button onClick={tryToLogout}>logout</button>
    </div>
  );
}
