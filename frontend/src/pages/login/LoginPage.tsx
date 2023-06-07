import { Form, Formik } from "formik";
import * as Yup from "yup";
import { FormikInput } from "../../components/formik/FormikInput";
import { FullScreenFormLayout } from "../../layouts/FullScreenFormLayout";

interface LoginFormValues {
  loginOrEmail: string;
  password: string;
}

export function LoginPage() {
  const initialValues: LoginFormValues = {
    loginOrEmail: "",
    password: "",
  };
  const loginSchema = Yup.object().shape({
    loginOrEmail: Yup.string().required("Login/email is required"),
    password: Yup.string().required("Password is required"),
  });

  function tryToLogin() {
    console.log("logged in");
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
                label="Login/email"
                name="loginOrEmail"
              />
              <FormikInput
                className="block w-full rounded-lg border border-form-border bg-form-bg p-2 placeholder-placeholder focus:outline-none"
                label="Password"
                name="password"
              />
            </div>
            <button
              type="submit"
              className="mt-8 h-12 w-40 rounded-md bg-base-button text-2xl enabled:hover:bg-base-button-hov disabled:cursor-not-allowed disabled:opacity-40"
              disabled={formik.isSubmitting || formik.isValidating}
            >
              create
            </button>
          </Form>
        )}
      </Formik>
    </FullScreenFormLayout>
  );
}
