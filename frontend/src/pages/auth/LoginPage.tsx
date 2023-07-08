import { Form, Formik, FormikHelpers, FormikValues } from "formik";
import * as Yup from "yup";
import { FormikInput } from "../../components/formik/FormikInput";
import { FullScreenFormLayout } from "../../layouts/FullScreenFormLayout";
import { useLoginMutation } from "../../store/apiSlice";
import { useNavigate } from "react-router-dom";
import { enqueueSnackbar } from "notistack";
import { LoginBody, LoginFormValues } from "../../interfaces/authInterfaces";

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

  function tryToLogin(
    values: FormikValues,
    formikHelpers: FormikHelpers<LoginFormValues>
  ) {
    const loginBody: LoginBody = {
      username: values.loginOrEmail,
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
      .then(() => {
        enqueueSnackbar("Logged in successfully", {
          variant: "success",
        });
        console.log("nawigacja po logowanie");
        navigate(`/my-profile`);
      })
      .catch((error) => {
        enqueueSnackbar("Logging failed", {
          variant: "error",
        });
        //TODO obsluzyc zle haslo
        //TODO tu tez bede pobieral my data wiec pewnie trzeba dodac czyszczenie przy logowaniu
      });

    formikHelpers.setSubmitting(false);
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
