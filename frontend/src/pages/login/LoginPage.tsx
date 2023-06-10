import { Form, Formik, FormikValues } from "formik";
import * as Yup from "yup";
import { FormikInput } from "../../components/formik/FormikInput";
import { FullScreenFormLayout } from "../../layouts/FullScreenFormLayout";
import { useLoginMutation } from "../../store/apiSlice";
import { useNavigate } from "react-router-dom";

interface LoginFormValues {
  loginOrEmail: string;
  password: string;
}

interface LoginBody {
  username: string;
  password: string;
}

export function LoginPage() {
  const [login] = useLoginMutation();
  const navigate = useNavigate();
  const initialValues: LoginFormValues = {
    loginOrEmail: "",
    password: "",
  };
  const loginSchema = Yup.object().shape({
    loginOrEmail: Yup.string().required("Login / email is required"),
    password: Yup.string().required("Password is required"),
  });

  function tryToLogin(values: FormikValues) {
    const loginBody = {
      username: values.login,
      password: values.password,
    };
    type loginBodyKey = keyof typeof loginBody;
    const loginBodyKeys = Object.keys(loginBody) as loginBodyKey[];
    const loginBodyAsString = loginBodyKeys
      .map(
        (key) =>
          encodeURIComponent(key) + "=" + encodeURIComponent(loginBody[key])
      )
      .join("&");

    login(loginBodyAsString)
      .unwrap()
      .then((result) => {
        // enqueueSnackbar("Group created successfully", {
        //   variant: "success",
        // });
        console.log(result);
      })
      .catch((error) => {
        console.log(error);
        // if (error.originalStatus === 200) {
        //   dispatch(apiSlice.util.resetApiState());
        //   navigate("/my-profile");
        // } else if (error.status === 401) {
        //   setShowLoginEmailPasswordIncorrect(true);
        // } else {
        //   enqueueSnackbar(handleErrorMessage(error), {
        //     variant: "error",
        //   });
        // }
      });
  }

  return (
    <FullScreenFormLayout>
      <Formik
        initialValues={initialValues}
        validationSchema={loginSchema}
        onSubmit={tryToLogin}
      >
        {(formik) => (
          <Form className="flex flex-col items-center justify-between rounded-lg bg-form-bg p-12">
            <div className="flex flex-col gap-4">
              <FormikInput
                className="block w-full rounded-lg border border-form-border bg-form-bg p-2 placeholder-placeholder focus:outline-none"
                label="Login / email"
                name="loginOrEmail"
              />
              <FormikInput
                className="block w-full rounded-lg border border-form-border bg-form-bg p-2 placeholder-placeholder focus:outline-none"
                label="Password"
                name="password"
                type="password"
              />
            </div>
            <button
              type="submit"
              className="mt-8 h-12 w-40 rounded-md bg-base-button text-2xl duration-200 enabled:hover:bg-base-button-hov disabled:cursor-not-allowed disabled:opacity-40"
              disabled={formik.isSubmitting || formik.isValidating}
            >
              login
            </button>
          </Form>
        )}
      </Formik>
    </FullScreenFormLayout>
  );
}
