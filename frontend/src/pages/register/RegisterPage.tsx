import { Form, Formik } from "formik";
import * as Yup from "yup";
import { FormikInput } from "../../components/formik/FormikInput";
import { FullScreenFormLayout } from "../../layouts/FullScreenFormLayout";

interface RegisterFormValues {
  login: string;
  password: string;
  email: string;
  birthDate: string;
  playingTimeStart: string;
  playingTimeEnd: string;
}

export function RegisterPage() {
  const initialValues: RegisterFormValues = {
    login: "",
    password: "",
    email: "",
    birthDate: "",
    playingTimeStart: "",
    playingTimeEnd: "",
  };
  const registerSchema = Yup.object().shape({
    login: Yup.string().required("Login is required"),
    password: Yup.string().required("Password is required"),
    email: Yup.string().required("Email is required"),
    birthDate: Yup.string().required(),
    playingTimeStart: Yup.string().required(),
    playingTimeEnd: Yup.string().required(),
  });

  function tryToRegister() {
    console.log("registerd in");
  }

  return (
    <FullScreenFormLayout>
      <Formik
        initialValues={initialValues}
        validationSchema={registerSchema}
        onSubmit={tryToRegister}
      >
        {(formik) => (
          <Form className="flex flex-col items-center justify-between rounded-lg bg-form-bg p-12">
            <div className="flex flex-col gap-4">
              <FormikInput
                className="block w-full rounded-lg border border-form-border bg-form-bg p-2 placeholder-placeholder focus:outline-none"
                label="Login"
                name="login"
              />
              <FormikInput
                className="block w-full rounded-lg border border-form-border bg-form-bg p-2 placeholder-placeholder focus:outline-none"
                label="Password"
                name="password"
                type="password"
              />
              <FormikInput
                className="block w-full rounded-lg border border-form-border bg-form-bg p-2 placeholder-placeholder focus:outline-none"
                label="Email"
                name="email"
              />
            </div>
            <button
              type="submit"
              className="mt-8 h-12 w-40 rounded-md bg-base-button text-2xl duration-200 enabled:hover:bg-base-button-hov disabled:cursor-not-allowed disabled:opacity-40"
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
